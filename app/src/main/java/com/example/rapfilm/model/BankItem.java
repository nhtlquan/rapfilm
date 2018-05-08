package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BankItem implements Parcelable {
    public static final Creator<BankItem> CREATOR = new C05571();
    private int id;
    private int imageID;
    private String name;

    static class C05571 implements Creator<BankItem> {
        C05571() {
        }

        public BankItem createFromParcel(Parcel in) {
            return new BankItem(in);
        }

        public BankItem[] newArray(int size) {
            return new BankItem[size];
        }
    }

    protected BankItem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.imageID = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.imageID);
    }

    public int describeContents() {
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getImageID() {
        return this.imageID;
    }
}
