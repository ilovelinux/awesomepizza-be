package com.awesomepizza.be.controller.customer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.awesomepizza.be.dto.AbstractAddressDto;
import com.awesomepizza.be.dto.request.AddressCreateDto;
import com.awesomepizza.be.dto.request.AddressUpdateDto;
import com.awesomepizza.be.dto.response.AddressResponseDto;
import com.awesomepizza.be.service.AddressService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(AddressController.class)
public class AddressControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AddressService addressService;

    private AddressResponseDto addressResponse;
    private AddressCreateDto newAddress;
    private AddressUpdateDto updatedAddress;

    protected void initAddress(AbstractAddressDto address) {
        address.setStreet("Via Roma");
        address.setStreetNo("1");
        address.setZip_cap("10000");
        address.setCity("Roma");
        address.setProv_st("RM");
        address.setState("Italy");
    }

    @BeforeEach
    void setUp() {
        addressResponse = new AddressResponseDto();
        initAddress(addressResponse);
        addressResponse.setId(1L);

        newAddress = new AddressCreateDto();
        initAddress(newAddress);

        updatedAddress = new AddressUpdateDto();
        initAddress(updatedAddress);
        updatedAddress.setId(1L);
    }

    @Test
    void getAddress() {
        when(addressService.getAddress(1L, 1L)).thenReturn(Mono.just(addressResponse));

        webClient.get().uri("/customer/address/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AddressResponseDto.class).isEqualTo(addressResponse);

        verify(addressService).getAddress(1L, 1L);
    }

    @Test
    void getAddressNotFound() {
        when(addressService.getAddress(1L, 1L)).thenReturn(Mono.empty());

        webClient.get().uri("/customer/address/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

        verify(addressService).getAddress(1L, 1L);
    }

    @Test
    void getAddressList() {
        List<AddressResponseDto> addressListObj= LongStream.range(1L, 3).mapToObj(addressResponse::setId).toList();
        Flux<AddressResponseDto> addressList = Flux.fromIterable(addressListObj);

        when(addressService.getAddresses(1L)).thenReturn(addressList);

        webClient.get().uri("/customer/address/list")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<AddressResponseDto>>() {}).isEqualTo(addressListObj);

        verify(addressService).getAddresses(1L);
    }

    @Test
    void getAddressListEmpty() {
        when(addressService.getAddresses(1L)).thenReturn(Flux.empty());

        webClient.get().uri("/customer/address/list")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class).isEqualTo(List.of());

        verify(addressService).getAddresses(1L);
    }

    @Test
    void createNewAddress() {
        when(addressService.addAddress(1L, newAddress)).thenReturn(Mono.just(addressResponse));

        webClient.post().uri("/customer/address/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newAddress)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AddressResponseDto.class).isEqualTo(addressResponse);

        verify(addressService).addAddress(1L, newAddress);
    }

    @Test
    void updateAddress() {
        when(addressService.updateAddress(1L, updatedAddress)).thenReturn(Mono.just(addressResponse));

        webClient.post().uri("/customer/address/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedAddress)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AddressResponseDto.class).isEqualTo(addressResponse);

        verify(addressService).updateAddress(1L, updatedAddress);
    }

    @Test
    void deleteAddress() {
        when(addressService.deleteAddress(1L, 1L)).thenReturn(Mono.just(true));

        webClient.delete().uri("/customer/address/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        verify(addressService).deleteAddress(1L, 1L);
    }

    @Test
    void deleteAddressNotFound() {
        when(addressService.deleteAddress(1L, 1L)).thenReturn(Mono.empty());

        webClient.delete().uri("/customer/address/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

        verify(addressService).deleteAddress(1L, 1L);
    }
}
