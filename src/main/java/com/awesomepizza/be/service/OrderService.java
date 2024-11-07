package com.awesomepizza.be.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.awesomepizza.be.dto.request.OrderCreateDto;
import com.awesomepizza.be.dto.response.OrderResponseDto;
import com.awesomepizza.be.enums.OrderStatusEnum;
import com.awesomepizza.be.model.AddressModel;
import com.awesomepizza.be.model.CustomerModel;
import com.awesomepizza.be.model.OrderAddressModel;
import com.awesomepizza.be.model.OrderModel;
import com.awesomepizza.be.model.ProductModel;
import com.awesomepizza.be.repository.AddressRepository;
import com.awesomepizza.be.repository.CustomerRepository;
import com.awesomepizza.be.repository.OrderAddressRepository;
import com.awesomepizza.be.repository.OrderRepository;
import com.awesomepizza.be.repository.ProductRepository;

import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderAddressRepository orderAddressRepository;

    @Autowired
    ProductRepository productRepository;

    public Mono<OrderResponseDto> createNewOrder(Long customerId, OrderCreateDto newOrder) {
        // Find the customer
        final Mono<CustomerModel> customer = customerRepository.findById(customerId);
        System.out.println("Customer: " + customer);

        // Find the address and copy it to the order address table
        final Mono<AddressModel> address = addressRepository.findById(newOrder.getDeliveryAddressId());
        System.out.println("Address: " + address);
        final Mono<OrderAddressModel> newOrderAddress = address.map(OrderAddressModel::of);
        System.out.println("New Order Address: " + newOrderAddress);
        // TODO: avoid duplicate addresses
        final Mono<OrderAddressModel> orderAddress = newOrderAddress.flatMap(orderAddressRepository::save).cache();

        final Mono<List<ProductModel>> products = Flux.fromIterable(newOrder.getProducts())
                .flatMap(productRepository::findById)
                .collectList()
                .cache();

        final Mono<OrderModel> order = Mono.zip(customer, orderAddress, products).map(tuple -> {
            final CustomerModel customerModel = tuple.getT1();
            final OrderAddressModel orderAddressModel = tuple.getT2();
            final List<ProductModel> productModels = tuple.getT3();
            return OrderModel.of(newOrder, customerModel, orderAddressModel, productModels);
        }).flatMap(orderRepository::save);

        return Mono.zip(order, orderAddress, products)
                .map(tuple -> {
                    final OrderModel orderModel = tuple.getT1();
                    final OrderAddressModel orderAddressModel = tuple.getT2();
                    final List<ProductModel> productModels = tuple.getT3();
                    return orderModel.setAddress(orderAddressModel).setProducts(productModels);
                })
                .map(OrderResponseDto::of);
    }

    private static final Sort DEFAULT_SORT = Sort.by("creationDate").descending();

    @Getter
    private static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(0, 10, DEFAULT_SORT);

    protected Mono<OrderModel> valorizeOrder(OrderModel orderMono) {
        return Mono.just(orderMono)
                .flatMap(order -> orderAddressRepository.findById(order.getAddressId())
                        .map(order::setAddress))
                .flatMap(order -> Flux.fromArray(order.getProductIds()).flatMap(productRepository::findById)
                        .collectList()
                        .map(order::setProducts));
    }

    public Flux<OrderResponseDto> getOrders(int page) {
        final PageRequest pageRequest = DEFAULT_PAGE_REQUEST.withPage(page);
        return orderRepository.findBy(pageRequest)
                .flatMap(this::valorizeOrder)
                .map(OrderResponseDto::of);
    }

    public Flux<OrderResponseDto> getPendingOrders(int page) {
        final PageRequest pageRequest = DEFAULT_PAGE_REQUEST.withPage(page);
        return orderRepository.findAllByStatus(OrderStatusEnum.PENDING,  pageRequest)
                .flatMap(this::valorizeOrder)
                .map(OrderResponseDto::of);
    }

    public Flux<OrderResponseDto> getActiveOrders(int page) {
        final PageRequest pageRequest = DEFAULT_PAGE_REQUEST.withPage(page);
        final List<OrderStatusEnum> inactiveStatuses = List.of(OrderStatusEnum.CANCELLED, OrderStatusEnum.DELIVERED);
        return orderRepository.findAllByStatusNotIn(inactiveStatuses,  pageRequest)
                .flatMap(this::valorizeOrder)
                .map(OrderResponseDto::of);
    }

    public Mono<OrderResponseDto> getOrder(Long orderNo) {
        return orderRepository.findById(orderNo)
                .flatMap(this::valorizeOrder)
                .map(OrderResponseDto::of);
    }

    public Mono<OrderResponseDto> setOrderStatus(Long orderNo, OrderStatusEnum status) {
        return orderRepository.findById(orderNo)
                .flatMap(order -> {
                    if (order.getStatus().getLevel() > status.getLevel()) {
                        return Mono.error(new IllegalArgumentException("Cannot set status to a lower level"));
                    }

                    if (order.getStatus().getLevel() == OrderStatusEnum.BLOCK_ORDER_VALUE()) {
                        return Mono.error(new IllegalArgumentException("Cannot set status of a blocked order"));
                    }

                    return Mono.just(order.setStatus(status));
                })
                .flatMap(orderRepository::save)
                .flatMap(this::valorizeOrder)
                .map(OrderResponseDto::of);
    }

    /**
     * A client may want to cancel an order but the pizza shop may already have
     * accepted it.
     *
     * @param orderNo
     * @return true if the order can be cancelled, false otherwise
     */
    public Mono<Boolean> deleteOrderCustomer(Long orderNo) {
        return orderRepository.findById(orderNo)
                .flatMap(order -> {
                    if (order.getStatus() != OrderStatusEnum.PENDING) {
                        return Mono.just(false);
                    }

                    return orderRepository
                            .save(order.setStatus(OrderStatusEnum.CANCELLED))
                            .map(savedOrder -> savedOrder.getStatus() == OrderStatusEnum.CANCELLED);
                });
    }

    public Mono<Boolean> deleteOrderShop(Long orderNo) {
        return orderRepository.findById(orderNo)
                .flatMap(order -> {
                    if (order.getStatus().getLevel() != OrderStatusEnum.BLOCK_ORDER_VALUE()) {
                        return Mono.just(false);
                    }

                    return orderRepository
                            .save(order.setStatus(OrderStatusEnum.CANCELLED))
                            .map(savedOrder -> savedOrder.getStatus() == OrderStatusEnum.CANCELLED);
                });
    }
}
