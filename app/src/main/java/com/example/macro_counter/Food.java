package com.example.macro_counter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Food {
    public String itemName, calories, proteinCnt, fat, cholesterol, fiber, username, email;
    public long timeInMillis;
    public String timeStamp;
    // Default constructor required for calls to

    public Food() {
    }

    // DataSnapshot.getValue(Food.class)
    public Food(JSONObject jsonObject) throws JSONException {
        itemName = jsonObject.getString("label");
        calories = jsonObject.getString("ENERC_KAL");
    }
    public static ArrayList<Food> fromJsonArray(JSONArray foodJsonArray) throws JSONException {
        ArrayList<Food> foods = new ArrayList<>();
        for (int i = 0; i < foodJsonArray.length(); i++) {
            JSONObject hintObject = foodJsonArray.getJSONObject(i);
            JSONObject foodObject = hintObject.getJSONObject("food");
            JSONObject nutObject = foodObject.getJSONObject("nutrients");

            String foodLabel = foodObject.getString("label");
            Integer calories = nutObject.getInt("ENERC_KCAL");
            Double protein = roundAvoid(nutObject.getDouble("PROCNT"), 2) ;
            Double fat = roundAvoid(nutObject.getDouble("FAT"), 2) ;
            Double chol = roundAvoid(nutObject.getDouble("CHOCDF"), 2) ;
//                Double fiber = nutObject.getDouble("FIBTG");

            foods.add(new Food(foodLabel, calories.toString(), protein.toString(), fat.toString(), chol.toString(), "0"));
        }
        return foods;
    }


    public Food(String itemName, String calories, String proteinCnt, String fat, String cholesterol, String fiber) {
        this.itemName = itemName;
        this.calories = calories;
        this.proteinCnt = proteinCnt;
        this.fat = fat;
        this.cholesterol = cholesterol;
        this.fiber = fiber;
    }



    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getProteinCnt() {
        return proteinCnt;
    }

    public void setProteinCnt(String proteinCnt) {
        this.proteinCnt = proteinCnt;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getFiber() {
        return fiber;
    }

    public void setFiber(String fiber) {
        this.fiber = fiber;
    }

    public void setUsername(String userame) { this.username = username; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setTimeInMillis(long timeInMillis) { this.timeInMillis = timeInMillis; }

    public long getTimeInMillis() { return timeInMillis; }

    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }

    public String getTimeStamp() { return timeStamp; }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}

