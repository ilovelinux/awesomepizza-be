package com.awesomepizza.be.controller.shop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awesomepizza.be.dto.response.ShopInfoResponseDto;
import com.awesomepizza.be.service.ShopService;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path="/shop", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/info")
    public Mono<ShopInfoResponseDto> getInfo() {
        return shopService.getInfo();
    }
}
