package com.awesomepizza.be.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awesomepizza.be.dto.response.OrderResponseDto;
import com.awesomepizza.be.enums.OrderStatusEnum;
import com.awesomepizza.be.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/shop/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderShopController {
    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public Flux<OrderResponseDto> getOrders(@RequestParam(defaultValue = "0") int page) {
        return orderService.getOrders(page);
    }

    @GetMapping("/list/active")
    public Flux<OrderResponseDto> getActiveOrders(@RequestParam(defaultValue = "0") int page) {
        return orderService.getActiveOrders(page);
    }

    @GetMapping("/list/pending")
    public Flux<OrderResponseDto> getPendingOrders(@RequestParam(defaultValue = "0") int page) {
        return orderService.getPendingOrders(page);
    }

    @GetMapping("/{orderNo}")
    public Mono<ResponseEntity<OrderResponseDto>> getOrder(ServerHttpResponse response, @PathVariable Long orderNo) {
        return orderService.getOrder(orderNo)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{orderNo}/setStatus")
    public Mono<ResponseEntity<OrderResponseDto>> setOrderStatus(@PathVariable Long orderNo, @RequestParam OrderStatusEnum status) {
        return orderService.setOrderStatus(orderNo, status)
                .map(ResponseEntity.ok()::body)
                .onErrorReturn(ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orderNo}")
    public Mono<ResponseEntity<Object>> deleteOrder(@PathVariable Long orderNo) {
        return orderService.deleteOrderShop(orderNo)
                .map(deleted -> {
                    if (!deleted)
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    return ResponseEntity.ok().build();
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
