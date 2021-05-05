package com.example.macro_counter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private List<Food> foods;
//    private List<Food> foodsFull;

    public FoodAdapter(Context context, List<Food> foods){
        this.context = context;
        this.foods = foods;
//        foodsFull = new ArrayList<>(foods);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fooditem_card, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);
        holder.bind(food);

    }

    @Override
    public int getItemCount() {
        return foods.size();
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

    public void filterList(ArrayList<Food> filteredList) {
        foods = filteredList;
        notifyDataSetChanged();
    }

//    @Override
//    public Filter getFilter() {
//        return foodFilter;
//    }
//
//    private Filter foodFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//             List<Food> filteredList = new ArrayList<>();
//
//             if (constraint == null || constraint.length() == 0) {
//                 filteredList.addAll(foodsFull);
//             } else {
//                 String filterPattern = constraint.toString().toLowerCase().trim();
//
//                 for (Food food : foodsFull) {
//                     if (food.getItemName().toLowerCase().startsWith(filterPattern)) {
//                         filteredList.add(food);
//                     }
//                 }
//             }
//
//             FilterResults filterResults  = new FilterResults();
//             filterResults.values = filteredList;
//
//             return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
//            foods.clear();
//            foods.addAll((List) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };
}
