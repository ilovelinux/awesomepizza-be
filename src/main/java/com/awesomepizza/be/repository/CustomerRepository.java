package com.awesomepizza.be.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.awesomepizza.be.model.CustomerModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerModel, Long> {
    Mono<CustomerModel> save(CustomerModel customerModel);

    Flux<CustomerModel> findAllBy(Pageable pageable);
    Flux<CustomerModel> findAllByName(String name, Pageable pageable);

    Mono<CustomerModel> findById(Long id);

    Mono<Void> delete(CustomerModel customerModel);
}
