package com.awesomepizza.be.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awesomepizza.be.dto.request.CustomerUpdateDto;
import com.awesomepizza.be.dto.response.CustomerResponseDto;
import com.awesomepizza.be.service.CustomerService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/profile")
    public Mono<CustomerResponseDto> getProfile() {
        return customerService.getCustomer(1L);
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerResponseDto> updateProfile(@RequestBody CustomerUpdateDto updatedCustomer) {
        return customerService.updateCustomer(1L, updatedCustomer);
    }
}
