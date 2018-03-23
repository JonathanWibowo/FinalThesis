package com.thesis.erpmegahjaya;

/**
 * Created by Jonathan on 3/20/2018.
 */

public class listMaterialInventory {

    private String name, code;
    private int quantity, price;

    public listMaterialInventory(String name, String code, int quantity, int price) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
