package com.sipios.refactoring.models;

public class Customer {
    // Customer represent a type of client with a finite list of items

    private Item[] items;
    private String type;

    public Customer() {
    }

    public Customer(Item[] is, String t) {
        this.items = is;
        this.type = t;
    }

    public Item[] getItems() {
        return items;
    }

    public String getType() {
        return type;
    }
}
