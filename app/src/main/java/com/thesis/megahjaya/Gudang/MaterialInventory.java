package com.thesis.megahjaya.Gudang;

public class MaterialInventory {

    private String name, code, description, group;
    private int quantity, minimum, price;

    public MaterialInventory(String name, String code, String description, String group, int quantity, int minimum, int price) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.group = group;
        this.quantity = quantity;
        this.minimum = minimum;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getPrice() {
        return price;
    }
}