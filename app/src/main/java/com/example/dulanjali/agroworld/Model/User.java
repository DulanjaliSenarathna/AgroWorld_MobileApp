package com.example.dulanjali.agroworld.Model;

public class User
{
    private String username,fullname,email,password,phone,imageURL,uid;

    public User() {
    }

    public User(String username, String fullname, String email, String password, String phone, String imageURL,String uid) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.imageURL = imageURL;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
