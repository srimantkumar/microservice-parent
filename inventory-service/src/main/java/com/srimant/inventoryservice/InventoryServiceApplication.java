package com.srimant.inventoryservice;

import com.srimant.inventoryservice.model.Inventory;
import com.srimant.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//        return args -> {
//            Inventory inventory =  new Inventory();
//            inventory.setSkuCode("iphone 13");
//            inventory.setQuantity(50);
//
//            Inventory inventory1 = new Inventory();
//            inventory1.setSkuCode("iphone 14 pro");
//            inventory1.setQuantity(2);
//
//            Inventory inventory2 = new Inventory();
//            inventory2.setSkuCode("boat earpod");
//            inventory2.setQuantity(20);
//
//            Inventory inventory3 = new Inventory();
//            inventory3.setSkuCode("Tide");
//            inventory3.setQuantity(30);
//
//            inventoryRepository.save(inventory);
//            inventoryRepository.save(inventory1);
//            inventoryRepository.save(inventory2);
//            inventoryRepository.save(inventory3);
//        };
//    }
}
