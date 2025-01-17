package com.sipios.refactoring.models;

public class Item {
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

    public double getPrice(double customerDiscount, boolean discountPeriod) {
        return InternalItem.valueOf(this.getType()).getPrice(discountPeriod) * this.getQuantity() * customerDiscount;
    }
}
