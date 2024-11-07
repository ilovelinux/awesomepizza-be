package com.awesomepizza.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

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
import com.awesomepizza.be.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AddressRepository addressRepository;

    @Mock
    OrderAddressRepository orderAddressRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    OrderService service;

    private CustomerModel customer;
    private OrderCreateDto newOrder;
    private AddressModel address;
    private OrderAddressModel orderAddress;
    private OrderModel order;
    private ProductModel product;

    @BeforeEach
    void setUp() {
        customer = new CustomerModel();
        customer.setId(1L);
        customer.setName("Mario Rossi");

        address = new AddressModel();
        address.setId(1L);
        address.setStreet("Via Roma");
        address.setStreetNo("1");
        address.setZip_cap("10000");
        address.setCity("Roma");
        address.setProv_st("RM");
        address.setState("Italy");

        orderAddress = new OrderAddressModel();
        orderAddress.setId(1L);
        orderAddress.setStreet("Via Roma");
        orderAddress.setStreetNo("1");
        orderAddress.setZip_cap("10000");
        orderAddress.setCity("Roma");
        orderAddress.setProv_st("RM");
        orderAddress.setState("Italy");

        product = new ProductModel();
        product.setId(1L);
        product.setName("Pizza Margherita");
        product.setIngredients(List.of("Tomato", "Mozzarella", "Basil"));
        product.setPrice(5.0);

        newOrder = new OrderCreateDto();
        newOrder.setProducts(List.of(product.getId()));
        newOrder.setDeliveryAddressId(1L);
        newOrder.setNotes(null);

        order = new OrderModel();
        order.setProductIds(new Long[] {product.getId()});
        order.setAddressId(orderAddress.getId());
        order.setNotes(null);
        order.setStatus(OrderStatusEnum.ACCEPTED);
    }

    @Test
    void createNewOrder() {
        when(customerRepository.findById(1L)).thenReturn(Mono.just(customer));
        when(addressRepository.findById(1L)).thenReturn(Mono.just(address));
        when(orderAddressRepository.save(any(OrderAddressModel.class))).thenReturn(Mono.just(orderAddress));
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));
        when(orderRepository.save(any(OrderModel.class))).thenReturn(Mono.just(order));

        final Mono<OrderResponseDto> serviceResponse = service.createNewOrder(1L, newOrder);

        StepVerifier.create(serviceResponse)
                .expectNextCount(1L)
                .verifyComplete();

        verify(customerRepository).findById(1L);
        verify(addressRepository).findById(1L);
        verify(orderAddressRepository).save(any(OrderAddressModel.class));
        verify(productRepository).findById(1L);
        verify(orderRepository).save(any(OrderModel.class));
    }

    @Test
    void createNewOrderInvalidCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Mono.empty());
        when(addressRepository.findById(1L)).thenReturn(Mono.just(address));
        when(orderAddressRepository.save(any(OrderAddressModel.class))).thenReturn(Mono.just(orderAddress));

        final Mono<OrderResponseDto> serviceResponse = service.createNewOrder(1L, newOrder);

        StepVerifier.create(serviceResponse)
                .expectNextCount(0)
                .verifyComplete();

        verify(customerRepository).findById(1L);
        verify(addressRepository).findById(1L);
        verify(orderAddressRepository).save(any(OrderAddressModel.class));
        verifyNoInteractions(orderRepository);
    }

    @Test
    void createNewOrderInvalidAddress() {
        when(customerRepository.findById(1L)).thenReturn(Mono.just(customer));
        when(addressRepository.findById(1L)).thenReturn(Mono.empty());

        final Mono<OrderResponseDto> serviceResponse = service.createNewOrder(1L, newOrder);

        StepVerifier.create(serviceResponse)
                .expectNextCount(0)
                .verifyComplete();

        verify(customerRepository).findById(1L);
        verify(addressRepository).findById(1L);
        verifyNoInteractions(orderAddressRepository);
        verifyNoInteractions(orderRepository);
    }

    @Test
    void getOrders() {
        final Stream<OrderModel> orders = LongStream.range(0, 10).mapToObj(order::setId);

        when(this.orderRepository.findBy(any(Pageable.class))).thenReturn(Flux.fromStream(orders));
        when(orderAddressRepository.findById(1L)).thenReturn(Mono.just(orderAddress));
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));

        final Flux<OrderResponseDto> serviceResponse = service.getOrders(0);

        StepVerifier.create(serviceResponse)
                .expectNextCount(10)
                .verifyComplete();

        verify(orderRepository).findBy(any(Pageable.class));
        verify(orderAddressRepository, times(10)).findById(1L);
        verify(productRepository, times(10)).findById(1L);
    }

    @Test
    void getNoOrders() {
        when(this.orderRepository.findBy(any(Pageable.class))).thenReturn(Flux.empty());

        final Flux<OrderResponseDto> serviceResponse = service.getOrders(0);

        StepVerifier.create(serviceResponse)
                .expectNextCount(0)
                .verifyComplete();

        verify(orderRepository).findBy(any(Pageable.class));
    }

    @Test
    void getOrder() {
        when(this.orderRepository.findById(1L)).thenReturn(Mono.just(order));
        when(orderAddressRepository.findById(1L)).thenReturn(Mono.just(orderAddress));
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));

        final Mono<OrderResponseDto> serviceResponse = service.getOrder(1L);

        StepVerifier.create(serviceResponse)
                .expectNextCount(1L)
                .verifyComplete();

        verify(orderRepository).findById(1L);
        verify(orderAddressRepository).findById(1L);
        verify(productRepository).findById(1L);
    }

    @Test
    void getOrderNotFound() {
        when(this.orderRepository.findById(1L)).thenReturn(Mono.empty());

        final Mono<OrderResponseDto> serviceResponse = service.getOrder(1L);

        StepVerifier.create(serviceResponse)
                .expectNextCount(0)
                .verifyComplete();

        verify(orderRepository).findById(1L);
    }

    @Test
    void deleteOrder() {
        when(this.orderRepository.findById(1L)).thenReturn(Mono.just(order.setStatus(OrderStatusEnum.PENDING)));
        when(this.orderRepository.save(any(OrderModel.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        final Mono<Boolean> serviceResponse = service.deleteOrderCustomer(1L);

        StepVerifier.create(serviceResponse)
                .expectNext(true)
                .verifyComplete();

        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(OrderModel.class));
    }

    @Test
    void deleteOrderAlreadyAccepted() {
        when(this.orderRepository.findById(1L)).thenReturn(Mono.just(order));

        final Mono<Boolean> serviceResponse = service.deleteOrderCustomer(1L);

        StepVerifier.create(serviceResponse)
                .expectNext(false)
                .verifyComplete();

        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(OrderModel.class));
    }

    @Test
    void deleteOrderNotFound() {
        when(this.orderRepository.findById(1L)).thenReturn(Mono.empty());

        final Mono<Boolean> serviceResponse = service.deleteOrderCustomer(1L);

        StepVerifier.create(serviceResponse)
                .expectNextCount(0)
                .verifyComplete();

        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(OrderModel.class));
    }
}
