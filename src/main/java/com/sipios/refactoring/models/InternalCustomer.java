package com.sipios.refactoring.models;

public enum InternalCustomer {
    STANDARD_CUSTOMER,
    PREMIUM_CUSTOMER,
    PLATINUM_CUSTOMER;

    public double getDiscount() {
        switch(this) {
            case STANDARD_CUSTOMER:
                return 1;
            case PREMIUM_CUSTOMER:
                return 0.9;
            case PLATINUM_CUSTOMER:
                return 0.5;
            default:
                return 0;
        }
    }
}
