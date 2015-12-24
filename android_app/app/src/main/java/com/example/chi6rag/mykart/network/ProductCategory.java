package com.example.chi6rag.mykart.network;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductCategory implements Parcelable {
    public static final String TAG = "product_category";
    public Integer id;
    public String name;

    public ProductCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    protected ProductCategory(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Creator<ProductCategory> CREATOR = new Creator<ProductCategory>() {
        public ProductCategory createFromParcel(Parcel source) {
            return new ProductCategory(source);
        }

        public ProductCategory[] newArray(int size) {
            return new ProductCategory[size];
        }
    };
}
