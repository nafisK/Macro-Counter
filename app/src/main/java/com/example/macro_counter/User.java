package com.example.macro_counter;

public class User {

    public String name;
    public String password;
    public double weight;
    public int heightFeet;
    public int heightInch;
    public int heightCm;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, double weight, int heightFeet, int heightInch) {
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.heightFeet = heightFeet;
        this.heightInch = heightInch;
    }

    public User(String name, String password, double weight, int heightCm) {
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.heightCm = heightCm;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(int heightFeet) {
        this.heightFeet = heightFeet;
    }

    public int getHeightInch() {
        return heightInch;
    }

    public void setHeightInch(int heightInch) {
        this.heightInch = heightInch;
    }

    public int getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(int heightCm) {
        this.heightCm = heightCm;
    }
}
