package com.paro.siparisverbot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;

//@SuppressWarnings("JpaAttributeTypeInspection")
@Data
@Entity
public class Order {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private int userId;
    private HashMap<Product, Integer> items;

}
