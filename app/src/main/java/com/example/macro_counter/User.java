package com.example.macro_counter;

public class User {

    public String email;
    public String name;
    public String password;
    public String weight;
    public String heightFeet;
    public String heightInch;
    public String heightCm;
    public String age;
    public String gender;
    public String activity;
    public String calorieIntake;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String email, String name, String password, String weight, String heightFeet, String heightInch, String heightCm, String age, String gender, String activity) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.heightFeet = heightFeet;
        this.heightInch = heightInch;
        this.heightCm = heightCm;
        this.age = age;
        this.gender = gender;
        this.activity = activity;
        this.calorieIntake = calculateCalorieIntake(weight, heightFeet, heightInch, heightCm, age, gender, activity);
    }

    public String calculateCalorieIntake(String weight, String heightFeet, String heightInch, String heightCm, String age, String gender, String activity ) {
        double CalorieIntake = 0;
        int wght = Integer.parseInt(weight);
        int ageInt = Integer.parseInt(age);

        if(heightCm.equals("")) {
            int heightFt = Integer.parseInt(heightFeet);
            int heightTotalInches = Integer.parseInt(heightInch) + (12 * heightFt);

            if(gender.equals("Male")){
                CalorieIntake = 66 + (6.3 * wght) + (12.9 * heightTotalInches) - (6.8 * ageInt);
            }
            else {
                CalorieIntake = 65 + (4.3 * wght) + (4.7 * heightTotalInches) - (4.7 * ageInt);
            }
        }
        else {
            int heightCmInt = Integer.parseInt(heightCm);
            double heightTotalInches = heightCmInt / 2.54;

            CalorieIntake = 66 + (6.3 * wght)  + (12.9 * heightTotalInches) - (6.8 * ageInt);

        }

        switch(activity) {
            case "Sedentary":
                CalorieIntake = CalorieIntake * 1.2;
                break;
            case "Light":
                CalorieIntake = CalorieIntake * 1.375;
                break;
            case "Moderate":
                CalorieIntake = CalorieIntake * 1.55;
                break;
            case "Hard":
                CalorieIntake = CalorieIntake * 1.725;
                break;
            case "Very Hard":
                CalorieIntake = CalorieIntake * 1.9;
                break;
        }

        return String.valueOf(CalorieIntake);
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
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
