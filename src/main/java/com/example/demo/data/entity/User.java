package com.example.demo.data.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_table")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private long id;

  @Column(name = "display_name")
  private String displayName;

  @Column(name = "display_surname")
  private String displaySurname;

  @Column(name = "phone_number", unique = true)
  private String phoneNumber;

  @Column(name = "email", unique = true)
  private String email;

  @CreationTimestamp
  @Column(name = "created_at", insertable = false, updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  public User() {
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public String getDisplaySurname() {
    return displaySurname;
  }

  public void setDisplaySurname(final String displaySurname) {
    this.displaySurname = displaySurname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public long getId() {
    return id;
  }

}
