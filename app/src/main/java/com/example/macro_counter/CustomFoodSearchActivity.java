package com.example.macro_counter;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CustomFoodSearchActivity extends AppCompatActivity implements OnClickListener{

    public static final String TAG = "CustomFoodSearchActivit";
    private RecyclerView recyclerView;
    FoodAdapterFb adapter;
    FoodAdapterFb adapter1;
    DatabaseReference mbase; // Firebase realtime Database
    SearchView svSearchFood;
    private Button btnNewFood;
    private Button btnSearchFB;
    private BottomNavigationView bottomNavigationView;
    private Query databaseRef;
    private Query foodQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_food_search);

        btnSearchFB = findViewById(R.id.btnSearchFB);
        EditText editText = findViewById(R.id.fb_edit_text);
        // Create a instance of the database and get its reference
        mbase = FirebaseDatabase.getInstance().getReference("/Foods");
        databaseRef = FirebaseDatabase.getInstance().getReference("/Foods");

        recyclerView = findViewById(R.id.rvCustomFoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provided by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(mbase, Food.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new FoodAdapterFb(options);
//        adapter = new FoodAdapterFb(options, getApplicationContext());

        // Connecting Adapter class with the Recycler view
        recyclerView.setAdapter(adapter);

        btnSearchFB.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchValue = editText.getText().toString();

                        //new query based on the user search in edit text
                        FirebaseRecyclerOptions<Food> options1 = new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods").orderByChild("itemName").startAt(searchValue).endAt(searchValue + "\uf8ff"), Food.class)
                                .build();
                        Log.d(TAG, "onSuccess");
                        adapter1 = new FoodAdapterFb(options1);

                        // Connecting Adapter class with the Recycler view
                        recyclerView.setAdapter(adapter1);

                        adapter.stopListening();
                        adapter1.startListening();

                        editText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    }});



        btnNewFood = findViewById(R.id.btnNewFood);
        btnNewFood.setOnClickListener(this);

    }


    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnNewFood:
                Intent intent = new Intent(this, NewDetailActivity.class);
                startActivity(intent);
                break;
        }
    }
}