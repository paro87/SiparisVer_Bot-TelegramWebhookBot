package com.paro.siparisverbot.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;

//@SuppressWarnings("JpaAttributeTypeInspection")
@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String orderId;
    private int userId;
    @ElementCollection
    private Map<Product, Integer> items;

}
