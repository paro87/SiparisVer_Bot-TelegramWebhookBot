package com.paro.siparisverbot.cache;

import com.paro.siparisverbot.model.Order;
import com.paro.siparisverbot.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartCache {
    private Map<Integer, Order> ordersInCart = new HashMap<>();

    public void saveItem(int userId, Product product, int quantity) {

        if (ordersInCart.containsKey(userId)){
            Order order = ordersInCart.get(userId);
            order.getItems().put(product, quantity);
        } else {
            Order order = new Order();
            order.setUserId(userId);
            Map<Product, Integer> orderList = new HashMap<>();
            orderList.put(product,quantity);
            order.setItems(orderList);
            ordersInCart.put(userId, order);
        }
    }

    public Order getOrderByUserId(int userId){
        return ordersInCart.get(userId);
    }

    public List<String> getOrdersAsList(int userId) {
        List<String> productList = new ArrayList<>();
        Order order = ordersInCart.getOrDefault(userId, new Order());
        order.getItems().forEach((k,v)->{
            productList.add(k+": "+v+" "+k.getUnitOfMeasurement());
        });
        return productList;
    }

    public void deleteOrder(int userId) {
        ordersInCart.remove(userId);
    }
}
