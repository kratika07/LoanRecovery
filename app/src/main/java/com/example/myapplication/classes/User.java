package com.example.myapplication.classes;

public class User {
    private int id;
    private String role;
    private String firstname;
    private String lastname;
    private String name;
    private String photo;
    private String email;
    private String phone;
    private String address;
    private String push_all;
    private String auth_token;



    public User(int id, String role, String firstname, String lastname, String name, String photo, String email, String phone, String address, String push_all, String auth_token) {
        this.id = id;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.push_all = push_all;
        this.auth_token = auth_token;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPush_all() {
        return push_all;
    }

    public void setPush_all(String push_all) {
        this.push_all = push_all;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
