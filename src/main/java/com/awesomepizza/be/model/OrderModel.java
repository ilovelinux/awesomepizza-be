package com.awesomepizza.be.model;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.awesomepizza.be.dto.request.OrderCreateDto;
import com.awesomepizza.be.enums.OrderStatusEnum;
import com.awesomepizza.be.enums.OrderTypeEnum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Table(name = "ORDERS")
@Getter
@Setter
@Accessors(chain = true)
public class OrderModel extends AbstractGenericModel {
    public static OrderModel of(OrderCreateDto order, CustomerModel customer, OrderAddressModel orderAddress, List<ProductModel> products) {
        final OrderModel orderModel = new OrderModel();

        orderModel.setCustomerId(customer.getId());
        orderModel.setCustomer(customer);
        orderModel.setType(order.getType());
        orderModel.setProductIds(products.stream().map(ProductModel::getId).toArray(Long[]::new));
        orderModel.setProducts(products);
        orderModel.setAddressId(orderAddress.getId());
        orderModel.setAddress(orderAddress);
        orderModel.setNotes(order.getNotes());
        orderModel.setStatus(OrderStatusEnum.PENDING);
        orderModel.setArchived(false);

        return orderModel;
    }
    @Id
    Long id;

    @Column("customer")
    @NotNull
    Long customerId;

    @Transient
    CustomerModel customer;

    OrderTypeEnum type;

    @Column("products")
    Long[] productIds;

    @Transient
    List<ProductModel> products;

    @Column("address")
    @NotNull
    Long addressId;

    @Transient
    OrderAddressModel address;

    @Size(max=50)
    String notes;

    OrderStatusEnum status;

    Boolean archived;

    @PastOrPresent
    LocalDateTime confirmationDate;

    @PastOrPresent
    LocalDateTime deliveryDate;
}
