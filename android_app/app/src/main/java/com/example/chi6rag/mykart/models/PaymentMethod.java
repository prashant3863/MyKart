package com.example.chi6rag.mykart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaymentMethod implements Parcelable {
    private static final String COD = "COD";
    Integer id;
    String name;
    @SerializedName("method_type")
    String methodType;

    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.methodType);
    }

    protected PaymentMethod(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.methodType = in.readString();
    }

    public static final Creator<PaymentMethod> CREATOR = new Creator<PaymentMethod>() {
        public PaymentMethod createFromParcel(Parcel source) {
            return new PaymentMethod(source);
        }

        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };

    public boolean isCOD() {
        return name.equals(COD);
    }
}
