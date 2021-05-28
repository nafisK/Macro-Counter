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
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class NewDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "NewDetailActivity";

    private EditText etItemName, etCalories, etProteinCnt, etFat, etCholesterolCDF, etFiber;
    private Button btnCancel, btnApply;

    private DatabaseReference mDatabase;
    FirebaseUser user;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    Food food;
    String uid;
    String userName;
    String foodKey;




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

        food = new Food(itemName, calories, proteinCnt, fat, cholesterol, fiber);
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String foodPostKey = database.getReference("FoodPost").push().getKey();
        foodKey = database.getReference("Foods").push().getKey();


        if (user != null) {

//             Goes through users to match the email of foods to email of users
            readUserName(new FirebaseCallback() {
                @Override
                public void onCallBack(String Uname) {
                    userName = Uname;
                    writeFoodData();


                }
            });


        }
        else {
            // No user is signed in
            Toast.makeText(NewDetailActivity.this, "Not Signed in! Please sign in!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(NewDetailActivity.this, LoginActivity.class));
        }

    }

    private void writeFoodData() {
//        food.setUsername(userName);
        food.setEmail(user.getEmail());

        Date date = new Date();
        long timeStamp = date.getTime();
        food.setTimeInMillis(timeStamp);
        food.setUserDisplayName(userName);
        System.out.println("PRINTING USER NAME 1: " + userName);
        System.out.println("PRINTING FROM FOOD OBJ: " + food.getUserDisplayName());



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
    }

    private void readUserName(FirebaseCallback firebaseCallback) {
        uid = user.getUid();
//        System.out.println("Printing User ID: " + uid);
        db = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get Post object and use the values to update the UI
                User userProfile = dataSnapshot.getValue(User.class);
                userName = userProfile.getName();

                firebaseCallback.onCallBack(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        db.addValueEventListener(userListener);
    }

    private interface FirebaseCallback {
        void onCallBack(String Uname);
    }



}