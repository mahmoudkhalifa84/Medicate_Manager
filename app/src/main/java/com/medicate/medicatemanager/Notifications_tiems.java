package com.medicate.medicatemanager;

public class Notifications_tiems {

    String body;
    String Date;
    String state;

    public String getBody() {
        return body;
    }

    public String getDate() {
        return Date;
    }

    public String getState() {
        return state;
    }

    public Notifications_tiems(String body, String date, String state) {
        this.body = body;
        Date = date;
        this.state = state;
    }
}
