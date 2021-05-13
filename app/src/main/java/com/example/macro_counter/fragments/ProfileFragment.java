package com.example.macro_counter.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macro_counter.FeedAdapter;
import com.example.macro_counter.FeedModel;
import com.example.macro_counter.Food;
import com.example.macro_counter.R;
import com.example.macro_counter.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private RecyclerView rvFeed;
    private DatabaseReference mDatabase;
    FeedAdapter adapter;
    private String userEmail, uid;
    private TextView tvName, tvWeight, tvAge, tvDailyCalories, tvTotalCalories;
    User currUser;
    private FirebaseDatabase database;
    String email;


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



        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User userProfile = dataSnapshot.getValue(User.class);

                tvName.setText(userProfile.getName());
                tvWeight.setText(userProfile.getWeight());
                tvAge.setText(userProfile.getAge());
                // add daily caloric intake
                // tvTotalCalories.setText(userProfile.calorieIntake);
                tvTotalCalories.setText(userProfile.calorieIntake);

                Log.d("ProfileFragment", String.valueOf(userProfile.getAge()));

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