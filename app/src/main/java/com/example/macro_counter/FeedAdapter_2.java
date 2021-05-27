package com.example.macro_counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        FeedModel model = list.get(position);
        holder.calories.setText(model.getCalories());
        holder.email.setText(model.getEmail());
        holder.fat.setText(model.getFat());
        holder.fiber.setText(model.getFiber());
        holder.itemName.setText(model.getItemName());
        holder.proteins.setText(model.getProteinCnt());

        // Parsing and Outputting TimeStamp
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formattedDate = new SimpleDateFormat("MMMM dd, Y");
        String timeStamp = formattedDate.format(model.getTimeInMillis());
        holder.timePosted.setText(timeStamp);

        if(position % 2 == 1) {
            holder.lyt_content_view.setBackgroundColor(Color.WHITE);
        } else {
            holder.lyt_content_view.setBackgroundColor(context.getResources().getColor(R.color.tanager_turquoise_trans));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username, email, timePosted, itemName, calories, proteins, fiber, fat;
        RelativeLayout lyt_content_view;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
//            EMAIL WILL BE CHANGED TO USERNAME WHEN THE BUG IS FIXED
//            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.username);
            timePosted = itemView.findViewById(R.id.timePosted);
            itemName = itemView.findViewById(R.id.itemName);
            calories = itemView.findViewById(R.id.calories);
            proteins = itemView.findViewById(R.id.proteins);
            fiber = itemView.findViewById(R.id.fiber);
            fat = itemView.findViewById(R.id.fats);
            lyt_content_view = itemView.findViewById(R.id.feedPostContainer);

        }
    }

}
