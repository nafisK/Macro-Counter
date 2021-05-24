package com.example.macro_counter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class NewDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etItemName, etCalories, etProteinCnt, etFat, etCholesterolCDF, etFiber;
    private Button btnCancel, btnApply;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Map mapTimeStamp = new HashMap();
    private Map mapUser = new HashMap();


    private String TAG = "NewDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Foods");

        etItemName = findViewById(R.id.textItemName);
        etCalories = findViewById(R.id.textCalories);
        etProteinCnt = findViewById(R.id.textProteinCnt);
        etFat = findViewById(R.id.textFat);
        etCholesterolCDF = findViewById(R.id.textCholesterolCDF);
        etFiber = findViewById(R.id.textFiber);

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnCancel:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnApply:
                registerFood();
                break;
        }
    }

    private void registerFood() {
        String itemName = etItemName.getText().toString().trim();
        String calories = etCalories.getText().toString().trim();
        String proteinCnt = etProteinCnt.getText().toString().trim();
        String fat = etFat.getText().toString().trim();
        String cholesterol = etCholesterolCDF.getText().toString().trim();
        String fiber = etFiber.getText().toString().trim();
        String itemId = mDatabase.push().getKey();

        if(itemName.isEmpty()){
            etItemName.setError("Item name is required!");
            etItemName.requestFocus();
            return;
        }

        if(calories.isEmpty()){
            etCalories.setError("Calories are required!");
            etCalories.requestFocus();
            return;
        }

        if(proteinCnt.isEmpty()){
            proteinCnt = "0";
            return;
        }

        if(fat.isEmpty()){
            fat = "0";
            return;
        }

        if(cholesterol.isEmpty()){
            cholesterol = "0";
            return;
        }

        if(fiber.isEmpty()){
            fiber = "0";
            return;
        }

        Food food = new Food(itemName, calories, proteinCnt, fat, cholesterol, fiber);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String foodPostKey = database.getReference("FoodPost").push().getKey();
        String foodKey = database.getReference("Foods").push().getKey();

        if (user != null) {
            // User is signed in
            String name = user.getDisplayName();
            food.setUsername(name);
            food.setEmail(user.getEmail());

            // Sets timestramp as a long
            long timestamp = currentTimeMillis();
            food.setTimeInMillis(timestamp);

            Log.i(TAG, "AFTER WRTING THE LONG: " + timestamp);

            // Sets timestamp as a String
            Date currDate = new Date();
            SimpleDateFormat formattedDate = new SimpleDateFormat("MMMM dd, Y");
            String timeStamp = formattedDate.format(currDate);
            food.setTimeStamp(timeStamp);

            FirebaseDatabase.getInstance().getReference("Foods")
                    .child(foodKey)
                    .setValue(food).addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(NewDetailActivity.this, "Food has been registered Successfully!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(NewDetailActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(NewDetailActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            // No user is signed in
            Toast.makeText(NewDetailActivity.this, "Not Signed in! Please sign in!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(NewDetailActivity.this, LoginActivity.class));
        }

    }


    public static String getTimeDate(Map<String, String> timestamp){
        try{
            Date netDate = (new Date(String.valueOf(timestamp)));
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            return sfd.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }
    public String getDatafTimeStamp(long timestamp){
        java.util.Date time=new java.util.Date(timestamp*1000);
        SimpleDateFormat pre = new SimpleDateFormat("EEE MM dd HH:mm:ss zzz yyyy");
        //Hear Define your returning date formate
        return pre.format(time);
    }
}