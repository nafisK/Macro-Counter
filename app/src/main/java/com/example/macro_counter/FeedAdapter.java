package com.example.macro_counter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;

public class FeedAdapter extends FirebaseRecyclerAdapter<FeedModel, FeedAdapter.ViewHolder> {

    private final String TAG = "FEED ACTIVITY";

    public FeedAdapter(@NonNull FirebaseRecyclerOptions<FeedModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_post, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView username, email, timePosted, itemName, calories, proteins, fiber, fat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // EMAIL WILL BE CHANGED TO USERNAME WHEN THE BUG IS FIXED
//            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.username);
            timePosted = itemView.findViewById(R.id.timePosted);
            itemName = itemView.findViewById(R.id.itemName);
            calories = itemView.findViewById(R.id.calories);
            proteins = itemView.findViewById(R.id.proteins);
            fiber = itemView.findViewById(R.id.fiber);
            fat = itemView.findViewById(R.id.fats);



        }
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FeedModel model) {
        holder.calories.setText(model.getCalories());
        // cholesterol
        holder.email.setText(model.getEmail());
        holder.fat.setText(model.getFat());
        holder.fiber.setText(model.getFiber());
        holder.itemName.setText(model.getItemName());
        holder.proteins.setText(model.getProteinCnt());
        // Parsing and Outputting TimeStamp
        SimpleDateFormat formattedDate = new SimpleDateFormat("MMMM dd, Y");
        String timeStamp = formattedDate.format(model.getTimeInMillis());
        holder.timePosted.setText(timeStamp);

    }

}
