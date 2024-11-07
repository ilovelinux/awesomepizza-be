package com.awesomepizza.be.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.awesomepizza.be.dto.request.AddressCreateDto;
import com.awesomepizza.be.dto.request.AddressUpdateDto;
import com.awesomepizza.be.dto.response.AddressResponseDto;
import com.awesomepizza.be.service.AddressService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/customer/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressCustomerController {
    @Autowired
    AddressService addressService;

    @GetMapping("/{addressId}")
    public Mono<ResponseEntity<AddressResponseDto>> getAddress(@PathVariable Long addressId) {
        return addressService.getAddress(1L, addressId)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public Flux<AddressResponseDto> getAddressList() {
        return addressService.getAddresses(1L);
    }

    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AddressResponseDto> createNewAddress(@RequestBody AddressCreateDto newAddress) {
        return addressService.addAddress(1L, newAddress);
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AddressResponseDto> updateAddress(@RequestBody AddressUpdateDto updatedAddress) {
        return addressService.updateAddress(1L, updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public Mono<ResponseEntity<Object>> deleteAddress(@PathVariable Long addressId) {
        return addressService.deleteAddress(1L, addressId)
                .map(v -> ResponseEntity.noContent().build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
