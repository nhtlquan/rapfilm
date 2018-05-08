package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TransactionHistory implements Parcelable {
    public static final Creator<TransactionHistory> CREATOR = new C05981();
    private int amount;
    private String avatar;
    private String currency;
    private int from_date;
    private String package_name;
    private int timestamp;
    private int to_date;
    private int total_day;
    private String transaction_id;
    private String type;
    private String username;

    static class C05981 implements Creator<TransactionHistory> {
        C05981() {
        }

        public TransactionHistory createFromParcel(Parcel in) {
            return new TransactionHistory(in);
        }

        public TransactionHistory[] newArray(int size) {
            return new TransactionHistory[size];
        }
    }

    protected TransactionHistory(Parcel in) {
        this.transaction_id = in.readString();
        this.type = in.readString();
        this.amount = in.readInt();
        this.timestamp = in.readInt();
        this.from_date = in.readInt();
        this.to_date = in.readInt();
        this.username = in.readString();
        this.avatar = in.readString();
        this.total_day = in.readInt();
        this.currency = in.readString();
        this.package_name = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.transaction_id);
        dest.writeString(this.type);
        dest.writeInt(this.amount);
        dest.writeInt(this.timestamp);
        dest.writeInt(this.from_date);
        dest.writeInt(this.to_date);
        dest.writeString(this.username);
        dest.writeString(this.avatar);
        dest.writeInt(this.total_day);
        dest.writeString(this.currency);
        dest.writeString(this.package_name);
    }

    public int describeContents() {
        return 0;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setFrom_date(int from_date) {
        this.from_date = from_date;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setTo_date(int to_date) {
        this.to_date = to_date;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_name() {
        return this.package_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTotal_day(int total_day) {
        this.total_day = total_day;
    }

    public int getTotal_day() {
        return this.total_day;
    }

    public String getCurrency() {
        return this.currency;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public int getFrom_date() {
        return this.from_date;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public int getTo_date() {
        return this.to_date;
    }

    public String getTransaction_id() {
        return this.transaction_id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getType() {
        return this.type;
    }
}
