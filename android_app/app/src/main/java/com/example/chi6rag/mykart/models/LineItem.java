package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LineItem implements Parcelable {
    public Variant variant;
    public Integer quantity;
    @SerializedName("single_display_amount")
    public String singleDisplayAmount;
    @SerializedName("display_amount")
    public String displayAmount;
    Integer id;
    @SerializedName("variant_id")
    Integer variantId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.quantity);
        dest.writeValue(this.variantId);
        dest.writeParcelable(this.variant, flags);
        dest.writeString(this.singleDisplayAmount);
        dest.writeString(this.displayAmount);
    }

    public LineItem() {
    }

    protected LineItem(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.variantId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.variant = in.readParcelable(Variant.class.getClassLoader());
        this.singleDisplayAmount = in.readString();
        this.displayAmount = in.readString();
    }

    public static final Creator<LineItem> CREATOR = new Creator<LineItem>() {
        public LineItem createFromParcel(Parcel source) {
            return new LineItem(source);
        }

        public LineItem[] newArray(int size) {
            return new LineItem[size];
        }
    };
}
