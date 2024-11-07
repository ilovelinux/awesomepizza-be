package com.awesomepizza.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awesomepizza.be.dto.response.OrderAddressDto;
import com.awesomepizza.be.model.AddressModel;
import com.awesomepizza.be.model.OrderAddressModel;
import com.awesomepizza.be.repository.OrderAddressRepository;

import reactor.core.publisher.Mono;

@Service
public class OrderAddressService {
    @Autowired
    OrderAddressRepository orderAddressRepository;

    public Mono<OrderAddressDto> copyFrom(AddressModel address) {
        final OrderAddressModel addressModel = OrderAddressModel.of(address);
        return orderAddressRepository.save(addressModel).map(OrderAddressDto::of);
    }
}
