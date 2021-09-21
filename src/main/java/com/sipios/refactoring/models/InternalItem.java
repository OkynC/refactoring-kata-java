package com.sipios.refactoring.models;

public enum InternalItem {
    TSHIRT,
    DRESS,
    JACKET;

    public double getDiscountPeriodPriceModificator() {
        switch(this) {
            case TSHIRT:
                return 1;
            case DRESS:
                return 0.8;
            case JACKET:
                return 0.9;
            default:
                return 0;
        }
    }

    public double getPrice(boolean discountPeriod) {
        switch (this) {
            case TSHIRT:
                if (discountPeriod) {
                    return 30 * this.getDiscountPeriodPriceModificator();
                }
                return 30;
            case DRESS:
                if (discountPeriod) {
                    return 50 * this.getDiscountPeriodPriceModificator();
                }
                return 50;
            case JACKET:
                if (discountPeriod) {
                    return 100 * this.getDiscountPeriodPriceModificator();
                }
                return 100;
            default:
                return 0;
        }
    }
}
