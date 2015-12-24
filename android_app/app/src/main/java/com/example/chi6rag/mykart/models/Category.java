package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.chi6rag.mykart.network.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class Category implements Parcelable {
    public Integer id;
    public String name;
    public List<ProductCategory> taxons;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeList(this.taxons);
    }

    protected Category(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.taxons = new ArrayList<ProductCategory>();
        in.readList(this.taxons, ProductCategory.class.getClassLoader());
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public List<ProductCategory> productCategories() {
        return taxons;
    }
}
