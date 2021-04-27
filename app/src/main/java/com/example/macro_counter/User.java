package com.example.macro_counter;

public class User {

    public String email;
    public String name;
    public String password;
    public String weight;
    public String heightFeet;
    public String heightInch;
    public String heightCm;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String email, String name, String password, String weight, String heightFeet, String heightInch, String heightCm) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.heightFeet = heightFeet;
        this.heightInch = heightInch;
        this.heightCm = heightCm;
    }

    public User(String email, String name, String password, String weight, String heightFeet, String heightInch) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.heightFeet = heightFeet;
        this.heightInch = heightInch;
    }

    public User(String email, String name, String password, String weight, String heightCm) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.heightCm = heightCm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(String heightFeet) {
        this.heightFeet = heightFeet;
    }

    public String getHeightInch() {
        return heightInch;
    }

    public void setHeightInch(String heightInch) {
        this.heightInch = heightInch;
    }

    public String getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(String heightCm) {
        this.heightCm = heightCm;
    }
}