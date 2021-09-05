package com.paro.siparisverbot.cache;

import com.paro.siparisverbot.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartCache {
    private Map<Integer, List<String>> ordersInCart = new HashMap<>();

    public void saveItem(int userId, Product product, int quantity) {
        String itemWithQuantity = product.getProductId()+", "+quantity+" "+product.getUnitOfMeasurement();
        if (ordersInCart.containsKey(userId)){
            ordersInCart.get(userId).add(itemWithQuantity);
        } else {
            List<String> ordersList = new ArrayList<>();
            ordersList.add(itemWithQuantity);
            ordersInCart.put(userId, ordersList);
        }
    }

    public List<String> getOrders(int userId) {
        List<String> strings = ordersInCart.getOrDefault(userId, new ArrayList<>());
        return strings;
    }
}
