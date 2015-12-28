package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Variant implements Parcelable {
    public Integer id;
    public String name;
    public List<Image> images;
    @SerializedName("display_price")
    public String displayPrice;
    @SerializedName("options_text")
    public String optionsText;
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(images);
        dest.writeString(this.displayPrice);
        dest.writeString(this.optionsText);
    }

    public Variant() {
    }

    protected Variant(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.displayPrice = in.readString();
        this.optionsText = in.readString();
    }

    public static final Creator<Variant> CREATOR = new Creator<Variant>() {
        public Variant createFromParcel(Parcel source) {
            return new Variant(source);
        }

        public Variant[] newArray(int size) {
            return new Variant[size];
        }
    };
}
