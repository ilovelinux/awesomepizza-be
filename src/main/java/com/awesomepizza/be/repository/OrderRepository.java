package com.awesomepizza.be.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.awesomepizza.be.model.OrderModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveCrudRepository<OrderModel, Long> {
    Mono<OrderModel> save(OrderModel newOrder);

    Flux<OrderModel> findBy(Pageable pageable);
    Mono<OrderModel> findById(Long id);
    Iterable<OrderModel> findByArchivedTrue();
    Iterable<OrderModel> findByArchivedFalse();
}
