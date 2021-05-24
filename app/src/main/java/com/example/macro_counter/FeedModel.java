package com.example.macro_counter;

public class FeedModel {

    // Needs to be in the exact same order as the DB
    String calories;
    String cholesterol;
    String email;
    String fat;
    String fiber;
    String itemName;
    String proteinCnt;
    String timeStamp;
    long timeInMillis;



    public FeedModel() {
    }

    public FeedModel(String calories, String cholesterol, String email, String fat, String fiber, String itemName, String proteinCnt, String timeStamp) {
        this.calories = calories;
        this.email = email;
        this.fat = fat;
        this.fiber = fiber;
        this.itemName = itemName;
        this.proteinCnt = proteinCnt;
        this.timeStamp = timeStamp;
    }

    public String getCalories() { return calories; }
    public String getEmail() { return email; }
    public String getFat() { return fat; }
    public String getFiber() { return fiber; }
    public String getItemName() { return itemName; }
    public String getTimeStamp() { return timeStamp; }
    public String getProteinCnt() { return proteinCnt; }
    public String getCholesterol() { return cholesterol; }
    public void setEmail(String email) { this.email = email; }
    public void setCalories(String calories) { this.calories = calories; }
    public void setCholesterol(String cholesterol) { this.cholesterol = cholesterol; }
    public void setFat(String fat) { this.fat = fat; }
    public void setFiber(String fiber) { this.fiber = fiber; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setProteinCnt(String proteinCnt) { this.proteinCnt = proteinCnt; }
    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }
    public long getTimeInMillis() { return timeInMillis; }
    public void setTimeInMillis(long timeInMillis) { this.timeInMillis = timeInMillis; }


}
