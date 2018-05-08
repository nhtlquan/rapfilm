package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class PackageInfo implements Parcelable {
    public static final Creator<PackageInfo> CREATOR = new C05741();
    private ArrayList<PackagePayment> day;
    private PackagePayment promoted;
    private ArrayList<PackagePayment> view;

    static class C05741 implements Creator<PackageInfo> {
        C05741() {
        }

        public PackageInfo createFromParcel(Parcel in) {
            return new PackageInfo(in);
        }

        public PackageInfo[] newArray(int size) {
            return new PackageInfo[size];
        }
    }

    protected PackageInfo(Parcel in) {
        this.day = in.createTypedArrayList(PackagePayment.CREATOR);
        this.view = in.createTypedArrayList(PackagePayment.CREATOR);
        this.promoted = (PackagePayment) in.readParcelable(PackagePayment.class.getClassLoader());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.day);
        dest.writeTypedList(this.view);
        dest.writeParcelable(this.promoted, flags);
    }

    public int describeContents() {
        return 0;
    }

    public void setPromoted(PackagePayment promoted) {
        this.promoted = promoted;
    }

    public PackagePayment getPromoted() {
        return this.promoted;
    }

    public void setDay(ArrayList<PackagePayment> day) {
        this.day = day;
    }

    public void setView(ArrayList<PackagePayment> view) {
        this.view = view;
    }

    public ArrayList<PackagePayment> getDay() {
        return this.day;
    }

    public ArrayList<PackagePayment> getView() {
        return this.view;
    }
}
