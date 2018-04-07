package com.thesis.megahjaya.Gudang;

public class ListMaterialInventory {

    private String name, code, description, group;
    private int quantity, price;

    public ListMaterialInventory(String name, String code, String description, String group, int quantity, int price) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.group = group;
        this.quantity = quantity;
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

    public int getPrice() {
        return price;
    }
}