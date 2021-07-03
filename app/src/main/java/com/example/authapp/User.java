package com.example.authapp;

public class User {
    private String Fullname;
    private String Age;
    private String Email;

    public User(String fullname, String age, String email) {
        Fullname = fullname;
        Age = age;
        Email = email;
    }

    public User() {
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
