package com.example.demo.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id", unique = true)
    private long id;

    @Column(name = "display_name")
    private String displayName;

    @Column (name = "display_surname")
    private String  displaySurname;

    @Column (name = "phone_number", unique = true)
    private String phoneNumber;

    @Column (name = "email", unique = true)
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

    public long getId() {
        return id;
    }
    public void setId() {
        this.id = id;
    }
}
