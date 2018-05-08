package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PackagePayment implements Parcelable {
    public static final Creator<PackagePayment> CREATOR = new C05751();
    private int amount;
    private String description;
    private String id;
    private String name;
    private long timestamp;
    private int total_day;
    private String type;

    static class C05751 implements Creator<PackagePayment> {
        C05751() {
        }

        public PackagePayment createFromParcel(Parcel in) {
            return new PackagePayment(in);
        }

        public PackagePayment[] newArray(int size) {
            return new PackagePayment[size];
        }
    }

    protected PackagePayment(Parcel in) {
        this.amount = in.readInt();
        this.total_day = in.readInt();
        this.timestamp = in.readLong();
        this.type = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.amount);
        dest.writeInt(this.total_day);
        dest.writeLong(this.timestamp);
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    public int describeContents() {
        return 0;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTotal_day(int total_day) {
        this.total_day = total_day;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getId() {
        return this.id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getTotal_day() {
        return this.total_day;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
