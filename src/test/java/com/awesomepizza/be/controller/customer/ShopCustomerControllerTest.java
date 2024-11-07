package com.awesomepizza.be.controller.customer;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.awesomepizza.be.dto.response.ShopAddressDto;
import com.awesomepizza.be.dto.response.ShopInfoResponseDto;
import com.awesomepizza.be.service.ShopService;

import reactor.core.publisher.Mono;

@WebFluxTest(ShopCustomerController.class)
public class ShopCustomerControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ShopService shopService;

    @Test
    void getInfo() {
        final ShopAddressDto addressInfo = new ShopAddressDto();
        addressInfo.setStreet("Via Roma");
        addressInfo.setStreetNo("1");
        addressInfo.setZip_cap("10000");
        addressInfo.setCity("Roma");
        addressInfo.setProv_st("RM");
        addressInfo.setState("Italy");

        final Map<Long, Set<Integer>> openingHours = LongStream.rangeClosed(0, 6).boxed().collect(Collectors.toMap(i->i, i->Set.of(9, 18)));
        final ShopInfoResponseDto shopInfo = new ShopInfoResponseDto("Awesome Pizza Test", addressInfo, openingHours);

        when(shopService.getInfo()).thenReturn(Mono.just(shopInfo));

        webClient.get().uri("/customer/shop/info")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ShopInfoResponseDto.class).isEqualTo(shopInfo);

        verify(shopService).getInfo();
    }
}
