package com.example.dulanjali.agroworld.Model;

public class Post
{
    String title,description,image,time,uid,email,uDp,user_name,pid;

    public Post() {
    }

    public Post(String title, String description, String image, String time, String uid, String email, String uDp, String user_name, String pid) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.time = time;
        this.uid = uid;
        this.email = email;
        this.uDp = uDp;
        this.user_name = user_name;
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getpId() {
        return pid;
    }

    public void setpId(String pid) {
        this.pid = pid;
    }
}
