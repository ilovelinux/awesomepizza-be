package com.awesomepizza.be.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.awesomepizza.be.model.OrderAddressModel;

import reactor.core.publisher.Mono;

public interface OrderAddressRepository extends ReactiveCrudRepository<OrderAddressModel, Long>  {
    Mono<OrderAddressModel> save(OrderAddressModel orderAddressModel);

    Mono<OrderAddressModel> findById(Long id);
}
