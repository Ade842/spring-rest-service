package com.example.demo.response;

public class UserResponse {

    private long id;
    private String displayName;
    private String  displaySurname;
    private String phoneNumber;
    private String email;


    public UserResponse(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDisplaySurname(String displaySurname) {
        this.displaySurname = displaySurname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {return displayName;}

    public String getDisplaySurname() {return displaySurname;}

    public String getPhoneNumber() {return phoneNumber;}

    public String getEmail() {return email;}
}
