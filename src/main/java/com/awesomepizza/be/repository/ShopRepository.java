package com.awesomepizza.be.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.awesomepizza.be.model.ShopModel;

import reactor.core.publisher.Mono;

public interface ShopRepository extends ReactiveCrudRepository<ShopModel, Long> {
    Mono<ShopModel> save(ShopModel shopModel);

    Mono<ShopModel> findById(Long id);
}
