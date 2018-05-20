package com.thesis.megahjaya.Penjualan.Search;

public class SearchMaterial {

    private String materialName;
    private Integer materialQuantity, materialPrice;

    public SearchMaterial(String materialName, Integer materialQuantity, Integer materialPrice) {
        this.materialName = materialName;
        this.materialQuantity = materialQuantity;
        this.materialPrice = materialPrice;
    }

    public String getMaterialName() {
        return materialName;
    }

    public Integer getMaterialQuantity() {
        return materialQuantity;
    }

    public Integer getMaterialPrice() {
        return materialPrice;
    }
}
