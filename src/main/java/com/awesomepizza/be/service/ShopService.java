package com.awesomepizza.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awesomepizza.be.dto.response.ShopInfoResponseDto;
import com.awesomepizza.be.model.ShopModel;
import com.awesomepizza.be.model.ShopOpeningHoursModel;
import com.awesomepizza.be.repository.ShopOpeningHoursRepository;
import com.awesomepizza.be.repository.ShopRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShopService {
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ShopOpeningHoursRepository shopOpeningHoursRepository;

    public Mono<ShopInfoResponseDto> getInfo() {
        final Mono<ShopModel> shopInfo = shopRepository.findById(1L);
        final Flux<ShopOpeningHoursModel> shopOpeningHours = shopOpeningHoursRepository.findAll();

        return Mono.zip(shopInfo, shopOpeningHours.collectList())
                .map(data -> ShopInfoResponseDto.of(data.getT1(), data.getT2()));
    }
}
