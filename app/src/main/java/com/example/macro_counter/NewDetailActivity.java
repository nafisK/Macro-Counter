package com.example.macro_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etItemName, etCalories, etProteinCnt, etFat, etCholesterolCDF, etFiber;
    private Button btnCancel, btnApply;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etItemName = findViewById(R.id.etItemName);
        etCalories = findViewById(R.id.etCalories);
        etProteinCnt = findViewById(R.id.etProteinCnt);
        etFat = findViewById(R.id.etFat);
        etCholesterolCDF = findViewById(R.id.etCholesterolCDF);
        etFiber = findViewById(R.id.etFiber);

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
        mDatabase.child("Foods").child(itemId).setValue(food);
    }


}