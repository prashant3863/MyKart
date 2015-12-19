package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CategoryResource implements Parcelable {
    public static String TAG = "category_tag";
    public final Integer id;
    public final String name;
    public Category root;

    CategoryResource(Integer id, String name, Category root) {
        this.id = id;
        this.name = name;
        this.root = root;
    }

    public List<ProductCategory> productCategories() {
        return root.taxons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.root, flags);
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    protected CategoryResource(Parcel in) {
        this.root = in.readParcelable(Category.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Creator<CategoryResource> CREATOR = new Creator<CategoryResource>() {
        public CategoryResource createFromParcel(Parcel source) {
            return new CategoryResource(source);
        }

        public CategoryResource[] newArray(int size) {
            return new CategoryResource[size];
        }
    };
}
