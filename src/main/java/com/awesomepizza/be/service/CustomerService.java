package com.awesomepizza.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awesomepizza.be.dto.request.CustomerUpdateDto;
import com.awesomepizza.be.dto.response.CustomerResponseDto;
import com.awesomepizza.be.model.CustomerModel;
import com.awesomepizza.be.repository.CustomerRepository;

import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Mono<CustomerResponseDto> getCustomer(Long customerId) {
        return customerRepository.findById(customerId).map(CustomerResponseDto::of);
    }

    public Mono<CustomerResponseDto> updateCustomer(Long customerId, CustomerUpdateDto newCustomer) {
        System.out.println("Updating customer with id: " + customerId + " to name: " + newCustomer.toString());
        Mono<CustomerModel> customer = customerRepository.findById(customerId);
        Mono<CustomerModel> updatedCustomer = customer.map(cust -> cust.setName(newCustomer.getName()));

        return updatedCustomer.flatMap(customerRepository::save).map(CustomerResponseDto::of);
    }

    public Mono<Void> deleteCustomer(Long customerId) {
        return Mono.error(new UnsupportedOperationException("Not implemented"));
    }
}
