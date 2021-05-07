package com.example.macro_counter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapterFb extends FirebaseRecyclerAdapter<Food, FoodAdapterFb.FoodsViewholder> {

    public FoodAdapterFb(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }
    // Function to bind the view in Food Entry view(here
    // "food_entry.xml") with data in
    // model class(here "Food.class")
    @Override
    protected void
    onBindViewHolder(@NonNull FoodsViewholder holder,
                     int position, @NonNull Food model)
    {

        // Add food name from model class (here
        // "Food.class")to appropriate view in Card
        // view (here "food_entry.xml")
        holder.tvAdapterFoodName.setText(model.getItemName());

        holder.tvAdapterCalories.setText("Calories:");

        // Add calorie value from model class (here
        // "Food.class")to appropriate view in Card
        // view (here "food_entry.xml")
        holder.tvAdapterCaloriesValue.setText(model.getCalories());
    }


    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public FoodsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_entry, parent, false);
        return new FoodAdapterFb.FoodsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class FoodsViewholder extends RecyclerView.ViewHolder {
        TextView tvAdapterFoodName, tvAdapterCalories, tvAdapterCaloriesValue;
        Button btnAdapterAdd;

        public FoodsViewholder(@NonNull View itemView) {
            super(itemView);

            tvAdapterFoodName = itemView.findViewById(R.id.tvAdapterFoodName);
            tvAdapterCalories = itemView.findViewById(R.id.tvAdapterCalories);
            tvAdapterCaloriesValue = itemView.findViewById(R.id.tvAdapterCaloriesValue);
            btnAdapterAdd = itemView.findViewById(R.id.btnAdapterAdd);
        }
    }

}
