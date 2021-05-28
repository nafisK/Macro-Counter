package com.example.macro_counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FeedAdapter_2 extends RecyclerView.Adapter<FeedAdapter_2.MyViewHolder> {

    Context context;
    ArrayList<FeedModel> list;

    public FeedAdapter_2(Context context, ArrayList<FeedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.feed_post, parent, false);
        return new MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        FeedModel model = list.get(position);
        holder.calories.setText(model.getCalories());
        holder.userDisplayName.setText(model.getUserDisplayName());
        holder.fat.setText(model.getFat());
        holder.fiber.setText(model.getFiber());
        holder.itemName.setText(model.getItemName());
        holder.proteins.setText(model.getProteinCnt());

        // Parsing and Outputting TimeStamp
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formattedDate = new SimpleDateFormat("MMMM dd, y");
        String timeStamp = formattedDate.format(model.getTimeInMillis());
        holder.timePosted.setText(timeStamp);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userDisplayName, email, timePosted, itemName, calories, proteins, fiber, fat;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
//            EMAIL WILL BE CHANGED TO USERNAME WHEN THE BUG IS FIXED
//            username = itemView.findViewById(R.id.username);
            userDisplayName = itemView.findViewById(R.id.username);
            timePosted = itemView.findViewById(R.id.timePosted);
            itemName = itemView.findViewById(R.id.itemName);
            calories = itemView.findViewById(R.id.calories);
            proteins = itemView.findViewById(R.id.proteins);
            fiber = itemView.findViewById(R.id.fiber);
            fat = itemView.findViewById(R.id.fats);

        }
    }

}
