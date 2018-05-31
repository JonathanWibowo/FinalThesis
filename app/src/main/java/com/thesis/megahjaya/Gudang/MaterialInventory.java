package com.thesis.megahjaya.Gudang;

public class MaterialInventory {

    private String name, code, measurement, group;
    private int quantity, minimum, price;

        public MaterialInventory(String name, String code, String measurement, String group, int quantity, int minimum, int price) {
        this.name = name;
        this.code = code;
        this.measurement = measurement;
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

    public String getMeasurement() {
        return measurement;
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


//    private String name, type, code, measurement;
//    private int quantity, price, minimum;
//
//    public MaterialInventory(String name, String type, String code, String measurement, int quantity, int price, int minimum) {
//        this.name = name;
//        this.type = type;
//        this.code = code;
//        this.measurement = measurement;
//        this.quantity = quantity;
//        this.price = price;
//        this.minimum = minimum;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getMeasurement() {
//        return measurement;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public int getMinimum() {
//        return minimum;
//    }
}