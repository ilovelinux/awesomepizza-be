package com.awesomepizza.be.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awesomepizza.be.dto.request.OrderCreateDto;
import com.awesomepizza.be.dto.response.OrderResponseDto;
import com.awesomepizza.be.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/customer/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<OrderResponseDto>> createNewOrder(@RequestBody OrderCreateDto orderDto) {
        return orderService.createNewOrder(1L, orderDto)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @GetMapping("/list")
    public Flux<OrderResponseDto> getOrders(@RequestParam(defaultValue = "0") int page) {
        return orderService.getOrders(page);
    }

    @GetMapping("/{orderNo}")
    public Mono<ResponseEntity<OrderResponseDto>> getOrder(ServerHttpResponse response, @PathVariable Long orderNo) {
        return orderService.getOrder(orderNo)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orderNo}")
    public Mono<ResponseEntity<Object>> deleteOrder(@PathVariable Long orderNo) {
        return orderService.deleteOrderCustomer(orderNo)
                .map(deleted -> {
                    if (!deleted)
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    return ResponseEntity.ok().build();
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
