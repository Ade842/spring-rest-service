package com.example.demo.request;

public class SavingAdvertisementsRequest {
    private String title;
    private String description;
    private long userId;

    public SavingAdvertisementsRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public long getUserId() {return userId;}

    public void setUserId(long userId) {this.userId = userId;}
}
