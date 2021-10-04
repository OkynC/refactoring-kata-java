package com.sipios.refactoring.models;

public enum InternalCustomer {
    STANDARD_CUSTOMER(1, 200, "standard customer"),
    PREMIUM_CUSTOMER(0.9, 800, "premium customer"),
    PLATINUM_CUSTOMER(0.5, 2000, "platinum customer");

    private final double discountModifier;
    private final double maxPrice;
    public final String logName;

    InternalCustomer(double discountModifier, double maxPrice, String logName) {
        this.discountModifier = discountModifier;
        this.maxPrice = maxPrice;
        this.logName = logName;
    }

    public double getDiscount() {
        return this.discountModifier;
    }

    public boolean overMaxPrice(double price) {
        return price > this.maxPrice;
    }

    public static InternalCustomer fromCustomer(Customer customer) {
        return valueOf(customer.getType());
    }
}
