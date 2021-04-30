package com.example.macro_counter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomFoodSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    FoodAdapterFb adapter;
    DatabaseReference mbase; // Firebase realtime Database
    SearchView svSearchFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_food_search);

        //svSearchFood = findViewById(R.id.test1); // crashes
        /*svSearchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(CustomFoodSearchActivity.this, "TextSubmit", Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(CustomFoodSearchActivity.this, "TextChange", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

        // Create a instance of the database and get its reference
        mbase = FirebaseDatabase.getInstance().getReference("/Foods");

        recyclerView = findViewById(R.id.rvFoods);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provided by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(mbase, Food.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new FoodAdapterFb(options);

        // Connecting Adapter class with the Recycler view
        recyclerView.setAdapter(adapter);
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
}