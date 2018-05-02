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

    protected ListMaterialPenjualan(Parcel in) {
        name = in.readString();
        code = in.readString();
        description = in.readString();
        group = in.readString();
        quantity = in.readInt();
        price = in.readInt();
    }

    public static final Creator<ListMaterialPenjualan> CREATOR = new Creator<ListMaterialPenjualan>() {
        @Override
        public ListMaterialPenjualan createFromParcel(Parcel in) {
            return new ListMaterialPenjualan(in);
        }

        @Override
        public ListMaterialPenjualan[] newArray(int size) {
            return new ListMaterialPenjualan[size];
        }
    };

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
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(description);
        dest.writeString(group);
        dest.writeInt(quantity);
        dest.writeInt(price);
    }
}
