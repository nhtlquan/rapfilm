package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cover implements Serializable {
    private String original;
    @SerializedName("320")
    private String s320;
    @SerializedName("640")
    private String s640;
    @SerializedName("960")
    private String s960;

    public Cover() {
    }

    public String getS960() {
        return this.s960;
    }

    public void setS960(String s960) {
        this.s960 = s960;
    }

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getS320() {
        return this.s320;
    }

    public void setS320(String s320) {
        this.s320 = s320;
    }

    public String getS640() {
        return this.s640;
    }

    public void setS640(String s640) {
        this.s640 = s640;
    }

    public String getLink() {
        return this.s640 == null ? this.original : this.s640;
    }

}
