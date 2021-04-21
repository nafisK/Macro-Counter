package com.example.macro_counter;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wFzGuGddmec8FLRPoxqWnuBHnj8YLsY0cfCpG8Im")
                .clientKey("8rZcfSt4loMhKT353aZzghgwwF2EZVlmIVmIhkPx")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
