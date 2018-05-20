package com.thesis.megahjaya.Penjualan;

import android.os.Parcel;
import android.os.Parcelable;

public class MaterialPenjualanSuccess implements Parcelable {

    private String name;
    private int buyQuantity, unitPrice, totalPrice;

    public MaterialPenjualanSuccess(String name, int buyQuantity, int unitPrice, int totalPrice) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public MaterialPenjualanSuccess() {
    }

    public String getName() {
        return name;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.buyQuantity);
        dest.writeInt(this.unitPrice);
        dest.writeInt(this.totalPrice);
    }

    protected MaterialPenjualanSuccess(Parcel in) {
        this.name = in.readString();
        this.buyQuantity = in.readInt();
        this.unitPrice = in.readInt();
        this.totalPrice = in.readInt();
    }

    public static final Parcelable.Creator<MaterialPenjualanSuccess> CREATOR = new Parcelable.Creator<MaterialPenjualanSuccess>() {
        @Override
        public MaterialPenjualanSuccess createFromParcel(Parcel source) {
            return new MaterialPenjualanSuccess(source);
        }

        @Override
        public MaterialPenjualanSuccess[] newArray(int size) {
            return new MaterialPenjualanSuccess[size];
        }
    };
}
