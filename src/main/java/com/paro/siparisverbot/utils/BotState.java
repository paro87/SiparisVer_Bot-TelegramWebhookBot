package com.paro.siparisverbot.utils;

public enum BotState {
    START("/start"),
    HELP("/help"),
    SELECT_MARKET("Stores"),
    A101_SELECTED("A101"),
    BIM_SELECTED("BIM"),
    SOK_SELECTED("SOK"),
    SPECIAL_OFFER("Special_offer"),

    ANOTHER_DAY_SPECIAL_OFFER("Another_Day_special_offer"),
    DATE("Date"),

    PRODUCTS("Products"),
    ORDER("Give order"),
    CART("Cart"),
    DELIVERY_ADDRESS("Delivery address"),
    PERSONAL_INFO("Personal info"),
    CONTACT_US("Contact us"),
    ORDER_INPUT("Order input"),
    CONTINUE("Continue"),
    FINISH("Finish"),
    PAYMENT("Payment"),
    RECEIPT("Download receipt"),
    UPDATE_DELIVERY_ADDRESS("Change delivery address"),
    DELIVERY_ADDRESS_INPUT("Delivery address input"),
    UPDATE_PERSONAL_INFO("Update personal info"),
    PERSONAL_INFO_INPUT("Personal info input"),
    CONTACT_US_INPUT("Contact us input"),
    WRONG_INPUT("Wrong input");
    public final String state;
    BotState(String state) {
        this.state=state;
    }
}
