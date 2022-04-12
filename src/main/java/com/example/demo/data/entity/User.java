package com.example.demo.data.entity;

import javax.persistence.*;

@Entity
@Table(name="user_table")
public class User {
    @GeneratedValue
    @Id
    private long id;

    @Column(name = "displayName")
    private String displayName;

    @Column (name = "displaySurname")
    private String  displaySurname;

    @Column (unique = true)
    private String phoneNumber;

    @Column (unique = true)
    private String email;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplaySurname() {
        return displaySurname;
    }

    public void setDisplaySurname(String displaySurname) {
        this.displaySurname = displaySurname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
