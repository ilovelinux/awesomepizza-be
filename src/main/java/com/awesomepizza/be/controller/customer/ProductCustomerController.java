package com.awesomepizza.be.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awesomepizza.be.dto.ProductDto;
import com.awesomepizza.be.model.ProductModel;
import com.awesomepizza.be.repository.ProductRepository;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/customer/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCustomerController {
    @Autowired
    ProductRepository productRepository;

    /**
     * @return all available products
     */
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> getProducts() {
        Flux<ProductModel> orderableProducts = productRepository.findByOrderableTrue();
        return orderableProducts.map(ProductDto::of);
    }
}
