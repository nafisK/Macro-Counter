package com.example.macro_counter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private ArrayList<Food> FoodList;

    public FoodAdapter(Context context, ArrayList<Food> FoodList){
        this.context = context;
        this.FoodList = FoodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fooditem_card, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = FoodList.get(position);
        holder.bind(food);

    }

    @Override
    public int getItemCount() {
        return FoodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFoodName;
        private TextView tvCalNumber;
//        private Button btAddFood;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvCalNumber = itemView.findViewById(R.id.tvCalNumber);
//            btAddFood = itemView.findViewById(R.id.btAddButton);
        }

        public void bind(Food food) {
            // Bind the food data to the view elements
            tvFoodName.setText(food.getItemName());
            tvCalNumber.setText("Calories: " + food.getCalories());
        }
    }
}
