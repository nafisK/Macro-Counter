package com.example.macro_counter.fragments;

import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private RecyclerView rvFeed;
    private DatabaseReference mDatabase;
    FeedAdapter adapter;
    private String userEmail, uid;
    private TextView tvName, tvWeight, tvAge;
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


        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        uid = user.getUid();


        /* Attempt for profile activity user's information
        currUser = new User();
        tvName = view.findViewById(R.id.tvName);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvAge = view.findViewById(R.id.tvAge);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Users");

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra("email");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").equals(email)) {
                        tvName.setText(ds.child("name").getValue(String.class));
                        tvWeight.setText(ds.child("weight").getValue(String.class));
                        tvAge.setText(ds.child("age").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled", error.toException());
            }
        });
        */

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