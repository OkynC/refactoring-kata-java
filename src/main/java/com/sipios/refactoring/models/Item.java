package com.sipios.refactoring.models;

public class Item {
    // Why quantity ??

    private String type;
    private int quantity;

    public Item() {
    }

    public Item(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
}
