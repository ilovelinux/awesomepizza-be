package com.awesomepizza.be.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.awesomepizza.be.model.ProductModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<ProductModel, Long>  {
    Mono<ProductModel> save(ProductModel product);

    Flux<ProductModel> findByOrderableTrue();
    Flux<ProductModel> findByNameContainingIgnoreCase(String name);

    Mono<Void> deleteById(Long id);
}
