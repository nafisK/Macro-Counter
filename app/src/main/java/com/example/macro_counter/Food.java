package com.example.macro_counter;

public class Food {
    public String itemName, calories, proteinCnt, fat, cholesterol, fiber;

    // Default constructor required for calls to
    // DataSnapshot.getValue(Food.class)
    public Food() {
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
}
