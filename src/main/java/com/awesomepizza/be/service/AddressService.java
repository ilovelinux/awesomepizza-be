package com.awesomepizza.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awesomepizza.be.dto.request.AddressCreateDto;
import com.awesomepizza.be.dto.request.AddressUpdateDto;
import com.awesomepizza.be.dto.response.AddressResponseDto;
import com.awesomepizza.be.model.AddressModel;
import com.awesomepizza.be.model.CustomerModel;
import com.awesomepizza.be.repository.AddressRepository;
import com.awesomepizza.be.repository.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Mono<AddressResponseDto> addAddress(Long customerId, AddressCreateDto newAddress) {
        Mono<CustomerModel> customer = customerRepository.findById(customerId);
        Mono<AddressModel> address = customer.map(cust -> AddressModel.of(newAddress, cust));

        return address.flatMap(addressRepository::save).map(AddressResponseDto::of);
    }

    public Flux<AddressResponseDto> getAddresses(Long customerId) {
        Mono<CustomerModel> customer = customerRepository.findById(customerId);
        return customer.flux().flatMap(cust -> addressRepository.findAllByCustomer(cust.getId()))
                .map(AddressResponseDto::of);
    }

    public Mono<AddressResponseDto> getAddress(Long customerId, Long addressId) {
        Mono<CustomerModel> customer = customerRepository.findById(customerId);
        return customer.flatMap(cust -> addressRepository.findByIdAndCustomer(addressId, cust.getId()))
                .map(AddressResponseDto::of);
    }

    public Mono<AddressResponseDto> updateAddress(Long customerId, AddressUpdateDto newAddress) {
        Mono<CustomerModel> customer = customerRepository.findById(customerId);
        Mono<AddressModel> address = customer
                .flatMap(cust -> addressRepository.findByIdAndCustomer(newAddress.getId(), cust.getId()));
        Mono<AddressModel> updatedAddress = address.map(addr -> {

            addr.setStreet(newAddress.getStreet());
            addr.setStreetNo(newAddress.getStreetNo());
            addr.setCity(newAddress.getCity());
            addr.setZip_cap(newAddress.getZip_cap());
            addr.setProv_st(newAddress.getProv_st());
            addr.setState(newAddress.getState());
            addr.setNotes(newAddress.getNotes());

            return addr;
        });

        return updatedAddress.flatMap(addressRepository::save).map(AddressResponseDto::of);
    }

    public Mono<Boolean> deleteAddress(Long customerId, Long addressId) {
        Mono<CustomerModel> customer = customerRepository.findById(customerId).cache();
        Mono<AddressModel> address = customer.flatMap(cust -> addressRepository.findByIdAndCustomer(addressId, cust.getId()));

        return address.flatMap(addressRepository::delete).map(v -> true).switchIfEmpty(Mono.empty());
    }
}
