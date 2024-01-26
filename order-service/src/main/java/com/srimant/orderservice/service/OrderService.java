package com.srimant.orderservice.service;

import com.srimant.orderservice.dto.InventoryResponse;
import com.srimant.orderservice.dto.OrderLineItemsDto;
import com.srimant.orderservice.dto.OrderRequest;
import com.srimant.orderservice.model.Order;
import com.srimant.orderservice.model.OrderLineItems;
import com.srimant.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call Inventory Service, and place order if product is in stock
        InventoryResponse[] inventoryResponses = webClient.get()
                                .uri("http://localhost:8092/api/inventory",
                                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(inventoryResponse -> {
                    OrderLineItemsDto orderLineItemsDto = orderRequest.getOrderLineItemsDtoList()
                            .stream()
                            .filter(dto -> dto.getSkuCode().equals(inventoryResponse.getSkuCode()))
                            .findFirst()
                            .orElse(null);

                    return orderLineItemsDto != null && orderLineItemsDto.getQuantity() <= inventoryResponse.getQuantityInStock();
                });

        if(allProductsInStock) {
            orderRepository.save(order);
            return "Order Placed Successfully.";
        }
        else
            return "Product is not available at the moment. Please try later. Thank You.";
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }

    public List<OrderLineItems> getAllPlacedOrderList() {
        List<OrderLineItems> orderLineItemsList = new ArrayList<>();

        orderRepository.findAll()
                //.stream()
                .forEach(order -> {
                    order.getOrderLineItems().forEach(item -> {
                        orderLineItemsList.add(item);
                    });
                });

        return orderLineItemsList;
    }
}
