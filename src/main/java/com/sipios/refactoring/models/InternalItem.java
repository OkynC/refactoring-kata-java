package com.sipios.refactoring.models;

public enum InternalItem {
    TSHIRT(30, 1),
    DRESS(50, 0.8),
    JACKET(100, 0.9);

    private final double price;
    private final double discountModifier;

    InternalItem(double price, double discountModifier) {
        this.price = price;
        this.discountModifier = discountModifier;
    }

    public double getPrice(boolean discountPeriod) {
        if (discountPeriod) {
            return this.price * this.discountModifier;
        }
        return this.price;
    }
}
