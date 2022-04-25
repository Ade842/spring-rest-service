package com.example.demo.request;

public class CreateUserRequest {

    private String displayName;
    private String  displaySurname;
    private String phoneNumber;
    private String email;


    public String getDisplayName() {
                return displayName;
            }

    public String getDisplaySurname() {
        return displaySurname;
    }

     public void setDisplaySurname(String displaySurname) {this.displaySurname = displaySurname;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;    }

    public String getEmail() {
                return email;
            }

    public void setEmail(String email) {this.email = email;}

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
