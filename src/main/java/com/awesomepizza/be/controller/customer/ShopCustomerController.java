package com.awesomepizza.be.controller.customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awesomepizza.be.dto.response.ShopInfoResponseDto;
import com.awesomepizza.be.service.ShopService;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path="/customer/shop", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopCustomerController {
    @Autowired
    ShopService shopService;

    @GetMapping("/info")
    public Mono<ShopInfoResponseDto> getInfo() {
        return shopService.getInfo();
    }
}
