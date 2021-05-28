package com.example.macro_counter.fragments;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macro_counter.FeedAdapter;
import com.example.macro_counter.FeedAdapter_2;
import com.example.macro_counter.FeedModel;
import com.example.macro_counter.ProfileFeedAdapter;
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

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private RecyclerView rvFeed;
    private DatabaseReference database;
    private DatabaseReference mDatabase;
    private DatabaseReference foodDatabaseRef;

//    FeedAdapter adapter;
        FeedAdapter_2 adapter;
//    ProfileFeedAdapter adapter;
    ArrayList<FeedModel> list;

    private String userEmail, uid;
    private TextView tvName, tvWeight, tvAge, tvDailyCalories, tvTotalCalories;
    User currUser;
    private DatabaseReference databaseRef;
    String email;


    Date date = new Date();
    long timeInMillis = date.getTime();
    SimpleDateFormat df = new SimpleDateFormat("MMMM dd, Y");


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

        // User Section

        tvName = view.findViewById(R.id.tvName);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvAge = view.findViewById(R.id.tvAge);
        tvDailyCalories = view.findViewById(R.id.tvDailyCalories);
        tvTotalCalories = view.findViewById(R.id.tvTotalCalories);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        uid = user.getUid();

        String searchKey1 = userEmail;
        String searchKey2 = df.format(timeInMillis);
        databaseRef = FirebaseDatabase.getInstance().getReference("Foods");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.child("email").exists() && dataSnapshot1.child("timeInMillis").exists()) {
                        if (dataSnapshot1.child("email").getValue().toString().equals(searchKey1) && df.format(dataSnapshot1.child("timeInMillis").getValue()).equals(searchKey2)) {

                            dataSnapshot1.child("calories").getValue().toString();
                            cValue[0] += Integer.parseInt(String.valueOf(dataSnapshot1.child("calories").getValue()));

                            Log.d(TAG, "email: " + userEmail + " cValue[0]: " + cValue[0]);
                        } else {
                        }
                    } else {
                    }
                }
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
//                email = userProfile.getEmail();
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






        // Recycler View Section

        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        database = FirebaseDatabase.getInstance().getReference("Foods");
        rvFeed.setHasFixedSize(true);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        email = user.getEmail();

        list = new ArrayList<>();

        adapter = new FeedAdapter_2(getContext(), list);
        rvFeed.setAdapter(adapter);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FeedModel feedModel = dataSnapshot.getValue(FeedModel.class);
                    String emailFromFood = feedModel.getEmail();
                    System.out.println("PRINTING EMAIL: " + email);
                    System.out.println("PRINTING EMAIL2: " + emailFromFood);
                    if (email.equals(emailFromFood)) {
//                        feedModel.setUserDisplayName(name);
                        list.add(feedModel);
                    }

                    // Sorting before displaying rows on feed post
                    Collections.sort(list, new Comparator<FeedModel>() {
                        @Override
                        public int compare(FeedModel o1, FeedModel o2) {
                            return Integer.valueOf((int) o2.getTimeInMillis()).compareTo((int) o1.getTimeInMillis());
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}