package com.example.macro_counter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macro_counter.FeedAdapter;
import com.example.macro_counter.FeedModel;
import com.example.macro_counter.R;
import com.example.macro_counter.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private RecyclerView rvFeed;
    private DatabaseReference mDatabase;
    FeedAdapter adapter;
    private String userEmail, uid;
    private TextView tvName, tvWeight, tvAge, tvDailyCalories, tvTotalCalories;
    User currUser;
    private DatabaseReference databaseRef;
    String email;

    String searchKey="justinparkcs@gmail.com|May 07, 2021";
    String[] str=searchKey.split("\\|");
    String searchKey1=str[0];
    String searchKey2=str[1];

    final int[] cValue = {0};


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tvName);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvAge = view.findViewById(R.id.tvAge);
        tvDailyCalories = view.findViewById(R.id.tvDailyCalories);
        tvTotalCalories = view.findViewById(R.id.tvTotalCalories);

        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        uid = user.getUid();


        databaseRef = FirebaseDatabase.getInstance().getReference("Foods");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.i(TAG, "Looking at child!");
                    if(dataSnapshot1.child("email").exists()&&dataSnapshot1.child("timeStamp").exists()) {
                        if(dataSnapshot1.child("email").getValue().toString().equals(searchKey1)&&dataSnapshot1.child("timeStamp").getValue().toString().equals(searchKey2)) {
                            //Do What You Want To Do.
                            Log.i(TAG, "Match!");
                            dataSnapshot1.child("calories").getValue().toString();
                            cValue[0] += Integer.parseInt(String.valueOf(dataSnapshot1.child("calories").getValue()));
                            Log.i(TAG, "Current Total Food Calories: " + cValue[0]);
                        }
                        else {
                            Log.i(TAG, "No Match!");
                        }
                    }
                    else {
                        Log.i(TAG, "children do not exist");
                    }
                }

                Log.i(TAG, "Calorie Before setText: " + cValue[0]);
                tvDailyCalories.setText(String.valueOf(cValue[0]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get Post object and use the values to update the UI
                User userProfile = dataSnapshot.getValue(User.class);

                tvName.setText(userProfile.getName());
                tvWeight.setText(userProfile.getWeight());
                tvAge.setText(userProfile.getAge());
//                // add daily caloric intake
//                Log.i(TAG, "Calorie Before setText: " + cValue[0]);
//                tvDailyCalories.setText(String.valueOf(cValue[0]));

                tvTotalCalories.setText(userProfile.calorieIntake);

                //Log.d("ProfileFragment", String.valueOf(userProfile.getAge()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(userListener);


        FirebaseRecyclerOptions<FeedModel> options =
                new FirebaseRecyclerOptions.Builder<FeedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods").orderByChild("email").equalTo(userEmail), FeedModel.class)
                        .build();


        adapter = new FeedAdapter(options);
        rvFeed.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}