package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {
    public static final String TAG = "product";

    public Integer id;
    public String name;
    public String description;
    public double price;
    public List<Image> images;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Image firstImageResource() {
        if (images.size() > 0) {
            return images.get(0);
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeDouble(this.price);
        dest.writeList(this.images);
    }

    protected Product(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.price = in.readDouble();
        this.images = new ArrayList<Image>();
        in.readList(this.images, Image.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
