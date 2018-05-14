package com.thesis.megahjaya.Penjualan;

import android.os.Parcel;
import android.os.Parcelable;

public class Penjualan implements Parcelable {

    String name;
    int id, quantity, unitPrice, totalUnitPrice;

    public Penjualan(String name, int quantity, int unitPrice, int totalUnitPrice) {
        this.name = name;
//        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalUnitPrice = totalUnitPrice;
    }

    public Penjualan() {
    }

    public String getName() {
        return name;
    }

//    public int getId() {
//        return id;
//    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getTotalUnitPrice() {
        return totalUnitPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.quantity);
        dest.writeInt(this.unitPrice);
        dest.writeInt(this.totalUnitPrice);
    }

    protected Penjualan(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.quantity = in.readInt();
        this.unitPrice = in.readInt();
        this.totalUnitPrice = in.readInt();
    }

    public static final Parcelable.Creator<Penjualan> CREATOR = new Parcelable.Creator<Penjualan>() {
        @Override
        public Penjualan createFromParcel(Parcel source) {
            return new Penjualan(source);
        }

        @Override
        public Penjualan[] newArray(int size) {
            return new Penjualan[size];
        }
    };
}