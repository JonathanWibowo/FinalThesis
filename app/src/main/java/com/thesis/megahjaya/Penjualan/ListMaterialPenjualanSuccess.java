package com.thesis.megahjaya.Penjualan;

import android.os.Parcel;
import android.os.Parcelable;

public class ListMaterialPenjualanSuccess implements Parcelable {

    private String name;
    private int buyQuantity, unitPrice, totalPrice;

    public ListMaterialPenjualanSuccess(String name, int buyQuantity, int unitPrice, int totalPrice) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public ListMaterialPenjualanSuccess() {
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

    protected ListMaterialPenjualanSuccess(Parcel in) {
        this.name = in.readString();
        this.buyQuantity = in.readInt();
        this.unitPrice = in.readInt();
        this.totalPrice = in.readInt();
    }

    public static final Parcelable.Creator<ListMaterialPenjualanSuccess> CREATOR = new Parcelable.Creator<ListMaterialPenjualanSuccess>() {
        @Override
        public ListMaterialPenjualanSuccess createFromParcel(Parcel source) {
            return new ListMaterialPenjualanSuccess(source);
        }

        @Override
        public ListMaterialPenjualanSuccess[] newArray(int size) {
            return new ListMaterialPenjualanSuccess[size];
        }
    };
}
