package com.example.escosta.omdbservice.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by escosta on 12/05/2017.
 */
public class Rating implements Parcelable {

    private String source;
    private String value;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.source);
        dest.writeString(this.value);
    }

    public Rating() {
    }

    protected Rating(Parcel in) {
        this.source = in.readString();
        this.value = in.readString();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel source) {
            return new Rating(source);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };
}


