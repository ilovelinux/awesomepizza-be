package com.awesomepizza.be.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.awesomepizza.be.model.AddressModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AddressRepository extends ReactiveCrudRepository<AddressModel, Long>  {
    Mono<AddressModel> save(AddressModel addressModel);

    Flux<AddressModel> findAllByCustomer(Long id);

    Mono<AddressModel> findByIdAndCustomer(Long id, Long customerId);

    Mono<Void> delete(AddressModel addressModel);
}
