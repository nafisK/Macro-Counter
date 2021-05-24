package com.example.macro_counter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.MyViewHolder>  {

    Context context;
    ArrayList<FeedModel> list;
    DatabaseReference database;





    /////// IGNORE FOR NOW!!!!


    public ProfileFeedAdapter(Context context, ArrayList<FeedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ProfileFeedAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.feed_post, parent, false);
        return new ProfileFeedAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfileFeedAdapter.MyViewHolder holder, int position) {
        FeedModel model = list.get(position);
        holder.calories.setText(model.getCalories());
        holder.email.setText(model.getEmail());
        holder.fat.setText(model.getFat());
        holder.fiber.setText(model.getFiber());
        holder.itemName.setText(model.getItemName());
        holder.proteins.setText(model.getProteinCnt());

        // Parsing and Outputting TimeStamp
        SimpleDateFormat formattedDate = new SimpleDateFormat("MMMM dd, Y");
        String timeStamp = formattedDate.format(model.getTimeInMillis());
        holder.timePosted.setText(timeStamp);

        database = FirebaseDatabase.getInstance().getReference("Foods");
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Query query = database.orderByChild("itemname").equalTo(model.getItemName());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            ds.getRef().removeValue(); // removes vales from firebase with tittle
                        }
                        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // if anything goes wrong, shows error
                        Toast.makeText(context, "Error deleting Item", Toast.LENGTH_SHORT).show();

                    }
                });
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username, email, timePosted, itemName, calories, proteins, fiber, fat;
        RelativeLayout relativeLayout;

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
            relativeLayout = itemView.findViewById(R.id.feedPostContainer);

        }

    }






}
