package com.thesis.megahjaya.Penjualan;

import android.os.Parcel;
import android.os.Parcelable;

public class MaterialPenjualan implements Parcelable {

    private String name, code, group;
    private int quantity, userInputQuantity, price;

    public MaterialPenjualan(String name, String code, String group, int quantity, int price) {
        this.name = name;
        this.code = code;
        this.group = group;
        this.quantity = quantity;
        this.price = price;
    }

    public void setUserInputQuantity(int userInputQuantity) {
        this.userInputQuantity = userInputQuantity;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.group);
        dest.writeInt(this.quantity);
        dest.writeInt(this.price);
    }

    protected MaterialPenjualan(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
        this.group = in.readString();
        this.quantity = in.readInt();
        this.price = in.readInt();
    }

    public static final Parcelable.Creator<MaterialPenjualan> CREATOR = new Parcelable.Creator<MaterialPenjualan>() {
        @Override
        public MaterialPenjualan createFromParcel(Parcel source) {
            return new MaterialPenjualan(source);
        }

        @Override
        public MaterialPenjualan[] newArray(int size) {
            return new MaterialPenjualan[size];
        }
    };
}
