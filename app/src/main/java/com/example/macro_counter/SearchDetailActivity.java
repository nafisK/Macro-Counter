package com.example.macro_counter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.parceler.Parcels;

public class SearchDetailActivity extends AppCompatActivity {

    TextView title;
    TextView calories;
    TextView proteinCnt;
    TextView fat;
    TextView cholesterol;
    TextView fiber;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        title = findViewById(R.id.tvItemNameValue);
        calories = findViewById(R.id.tvCaloriesValue);
        proteinCnt = findViewById(R.id.tvProteinCntValue);
        fat = findViewById(R.id.tvFatValue);
        cholesterol = findViewById(R.id.tvCholesterolCDFValue);
        fiber = findViewById(R.id.tvFiberValue);
        btnCancel = findViewById(R.id.btnCancel);

        Food food = Parcels.unwrap(getIntent().getParcelableExtra("food"));

        title.setText(food.getItemName());
        calories.setText(food.getCalories());
        proteinCnt.setText(food.proteinCnt);
        fat.setText(food.getFat());
        cholesterol.setText(food.getCholesterol());
        fiber.setText(food.getFiber());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainAcitivity();
            }
        });

    }

    private void backToMainAcitivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}