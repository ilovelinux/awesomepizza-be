package com.awesomepizza.be.controller.customer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.awesomepizza.be.dto.response.OrderAddressDto;
import com.awesomepizza.be.dto.response.OrderResponseDto;
import com.awesomepizza.be.enums.OrderStatusEnum;
import com.awesomepizza.be.service.OrderService;

import reactor.core.publisher.Mono;

@WebFluxTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private OrderService orderService;

    private OrderAddressDto orderAddress;
    private OrderResponseDto orderResponse;

    @BeforeEach
    void setUp() {
        orderAddress = new OrderAddressDto();
        orderAddress.setStreet("Via Roma");
        orderAddress.setStreetNo("1");
        orderAddress.setZip_cap("10000");
        orderAddress.setCity("Roma");
        orderAddress.setProv_st("RM");
        orderAddress.setState("Italy");

        orderResponse = new OrderResponseDto();
        orderResponse.setId(1L);
        orderResponse.setProducts(List.of());
        orderResponse.setDeliveryAddress(orderAddress);
        orderResponse.setNotes(null);
        orderResponse.setStatus(OrderStatusEnum.ACCEPTED);
    }

    @Test
    void getOrder() {
        when(orderService.getOrder(1L)).thenReturn(Mono.just(orderResponse));

        webClient.get().uri("/customer/orders/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderResponseDto.class).isEqualTo(orderResponse);

        verify(orderService).getOrder(1L);
    }

    @Test
    void getOrderNotFound() {
        when(orderService.getOrder(1L)).thenReturn(Mono.empty());

        webClient.get().uri("/customer/orders/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

        verify(orderService).getOrder(1L);
    }

    @Test
    void deleteOrder() {
        when(orderService.deleteOrderCustomer(1L)).thenReturn(Mono.just(true));

        webClient.delete().uri("/customer/orders/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(orderService).deleteOrderCustomer(1L);
    }

    @Test
    void deleteOrderAlreadyAccepted() {
        when(orderService.deleteOrderCustomer(1L)).thenReturn(Mono.just(false));

        webClient.delete().uri("/customer/orders/1")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody().isEmpty();

        verify(orderService).deleteOrderCustomer(1L);
    }

    @Test
    void deleteOrderNotFound() {
        when(orderService.deleteOrderCustomer(1L)).thenReturn(Mono.empty());

        webClient.delete().uri("/customer/orders/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

        verify(orderService).deleteOrderCustomer(1L);
    }
}
