package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    public static final Creator<User> CREATOR = new C06061();
    @SerializedName("access_token")
    private String accessToken;
    private String avatar;
    private String birthday;
    private String email;
    private long expires_in;
    private long expiry_date;
    private int favourite;
    private String fullname;
    private String gender;
    private String invite_code;
    private boolean isVip;
    private String password;
    private String phone;
    private int recent;
    @SerializedName("refresh_token")
    private String refreshToken;
    private int total_view_vip;
    private String type;
    private String userId;
    private String username;
    private boolean using_code;
    private boolean verified;

    static class C06061 implements Creator<User> {
        C06061() {
        }

        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    }

    protected User(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.userId = in.readString();
        this.accessToken = in.readString();
        this.refreshToken = in.readString();
        this.expires_in = in.readLong();
        this.fullname = in.readString();
        this.username = in.readString();
        this.avatar = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.birthday = in.readString();
        this.gender = in.readString();
        this.verified = in.readByte() != (byte) 0;
        this.invite_code = in.readString();
        this.expiry_date = in.readLong();
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isVip = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.using_code = z2;
        this.password = in.readString();
        this.total_view_vip = in.readInt();
        this.recent = in.readInt();
        this.favourite = in.readInt();
        this.type = in.readString();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isUsing_code() {
        return this.using_code;
    }

    public void setUsing_code(boolean using_code) {
        this.using_code = using_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getInvite_code() {
        return this.invite_code;
    }

    public void setRecent(int recent) {
        this.recent = recent;
    }

    public int getRecent() {
        return this.recent;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getFavourite() {
        return this.favourite;
    }

    public void setVip(boolean vip) {
        this.isVip = vip;
    }

    public boolean isVip() {
        return this.isVip;
    }

    public void setExpiry_date(long expiry_date) {
        this.expiry_date = expiry_date;
    }

    public long getExpiry_date() {
        return this.expiry_date;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpires_in() {
        return this.expires_in;
    }

    public void setTotal_view_vip(int total_view_vip) {
        this.total_view_vip = total_view_vip;
    }

    public int getTotal_view_vip() {
        return this.total_view_vip;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = (byte) 1;
        dest.writeString(this.userId);
        dest.writeString(this.accessToken);
        dest.writeString(this.refreshToken);
        dest.writeLong(this.expires_in);
        dest.writeString(this.fullname);
        dest.writeString(this.username);
        dest.writeString(this.avatar);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.birthday);
        dest.writeString(this.gender);
        dest.writeByte((byte) (this.verified ? 1 : 0));
        dest.writeString(this.invite_code);
        dest.writeLong(this.expiry_date);
        if (this.isVip) {
            b = (byte) 1;
        } else {
            b = (byte) 0;
        }
        dest.writeByte(b);
        if (!this.using_code) {
            b2 = (byte) 0;
        }
        dest.writeByte(b2);
        dest.writeString(this.password);
        dest.writeInt(this.total_view_vip);
        dest.writeInt(this.recent);
        dest.writeInt(this.favourite);
        dest.writeString(this.type);
    }
}
