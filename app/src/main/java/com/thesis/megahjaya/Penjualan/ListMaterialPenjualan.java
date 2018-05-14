package com.thesis.megahjaya.Penjualan;

import android.os.Parcel;
import android.os.Parcelable;

public class ListMaterialPenjualan implements Parcelable {

    private String name, code, description, group;
    private int quantity, price;

    public ListMaterialPenjualan(String name, String code, String description, String group, int quantity, int price) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.description);
        dest.writeString(this.group);
        dest.writeInt(this.quantity);
        dest.writeInt(this.price);
    }

    protected ListMaterialPenjualan(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
        this.description = in.readString();
        this.group = in.readString();
        this.quantity = in.readInt();
        this.price = in.readInt();
    }

    public static final Parcelable.Creator<ListMaterialPenjualan> CREATOR = new Parcelable.Creator<ListMaterialPenjualan>() {
        @Override
        public ListMaterialPenjualan createFromParcel(Parcel source) {
            return new ListMaterialPenjualan(source);
        }

        @Override
        public ListMaterialPenjualan[] newArray(int size) {
            return new ListMaterialPenjualan[size];
        }
    };
}
