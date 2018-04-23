package com.example.lequan.film.model;

import java.io.Serializable;

public class FacebookFriend implements Serializable {
    private String avatarUrl;
    private String userId;
    private String userName;

    public FacebookFriend(String userId, String userName, String avatarUrl) {
        this.userId = userId;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }
}
