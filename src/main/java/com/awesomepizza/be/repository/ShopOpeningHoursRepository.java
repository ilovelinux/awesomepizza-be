package com.awesomepizza.be.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.awesomepizza.be.model.ShopOpeningHoursModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShopOpeningHoursRepository extends ReactiveCrudRepository<ShopOpeningHoursModel, Long> {
    Mono<ShopOpeningHoursModel> save(ShopOpeningHoursModel shopOpeningHoursModel);

    Flux<ShopOpeningHoursModel> findAll();

    Mono<ShopOpeningHoursModel> findById(Long id);
}
