package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResource implements Parcelable {
    public static final String WOMEN = "women";
    public static String TAG = "category_tag";
    public static String MEN = "men";
    public final Integer id;
    public final String name;
    @SerializedName("root")
    public Category category;

    public List<ProductCategory> productCategories() {
        return category.productCategories();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.category, flags);
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    protected CategoryResource(Parcel in) {
        this.category = in.readParcelable(Category.class.getClassLoader());
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

    public int productCategoriesCount() {
        return category.productCategoriesCount();
    }
}
