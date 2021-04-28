package com.example.macro_counter;

import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class FoodPost {

    private FirebaseUser user;
    private Map<String, String> timestamp;
    private Food food = new Food();

    public FoodPost(FirebaseUser user, String timeDate, Food food) {
    }

    public FoodPost(FirebaseUser user, Map<String, String> timestamp, Food food) {
        this.user = user;
        this.timestamp = timestamp;
        this.food = food;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public Map<String, String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Map<String, String> timestamp) {
        this.timestamp = timestamp;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
