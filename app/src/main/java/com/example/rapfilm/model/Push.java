package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Push implements Parcelable {
    public static final Creator<Push> CREATOR = new C05851();
    private String alert;
    private Id object;
    private String type;
    private String uri;

    static class C05851 implements Creator<Push> {
        C05851() {
        }

        public Push createFromParcel(Parcel in) {
            return new Push(in);
        }

        public Push[] newArray(int size) {
            return new Push[size];
        }
    }

    protected Push(Parcel in) {
        this.alert = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
        this.object = (Id) in.readParcelable(Id.class.getClassLoader());
    }

    public String getAlert() {
        return this.alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Id getObject() {
        return this.object;
    }

    public void setObject(Id object) {
        this.object = object;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.alert);
        dest.writeString(this.type);
        dest.writeString(this.uri);
        dest.writeParcelable(this.object, flags);
    }
}
