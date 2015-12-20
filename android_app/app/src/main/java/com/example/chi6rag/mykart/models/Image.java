package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {
    public Integer id;
    public String position;

    @SerializedName("attachment_width")
    public Integer attachmentWidth;
    @SerializedName("attachment_height")
    public Integer attachmentHeight;
    @SerializedName("mini_url")
    public String miniUrl;
    @SerializedName("small_url")
    public String smallUrl;
    @SerializedName("product_url")
    public String productUrl;
    @SerializedName("large_url")
    public String largeUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.position);
        dest.writeValue(this.attachmentWidth);
        dest.writeValue(this.attachmentHeight);
        dest.writeString(this.miniUrl);
        dest.writeString(this.smallUrl);
        dest.writeString(this.productUrl);
        dest.writeString(this.largeUrl);
    }

    protected Image(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.position = in.readString();
        this.attachmentWidth = (Integer) in.readValue(Integer.class.getClassLoader());
        this.attachmentHeight = (Integer) in.readValue(Integer.class.getClassLoader());
        this.miniUrl = in.readString();
        this.smallUrl = in.readString();
        this.productUrl = in.readString();
        this.largeUrl = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
