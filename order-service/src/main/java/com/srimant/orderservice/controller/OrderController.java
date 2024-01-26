package com.srimant.orderservice.controller;

import com.srimant.orderservice.dto.OrderRequest;
import com.srimant.orderservice.model.Order;
import com.srimant.orderservice.model.OrderLineItems;
import com.srimant.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<OrderLineItems> showAllOrdersPlaced() {
       return orderService.getAllPlacedOrderList();
    }
}
