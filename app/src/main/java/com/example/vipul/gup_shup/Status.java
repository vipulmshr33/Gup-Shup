package com.example.vipul.gup_shup;

/**
 * Created by VIPUL on 07-05-2016.
 */
public class Status {
    private String username,status;

    public Status(String username, String status) {

        this.setUsername(username);
        this.setStatus(status);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
