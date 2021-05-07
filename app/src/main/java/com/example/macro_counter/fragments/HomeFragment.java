package com.example.macro_counter.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macro_counter.FeedAdapter;
import com.example.macro_counter.FeedModel;
import com.example.macro_counter.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private RecyclerView rvFeed;
    private DatabaseReference mDatabase;
    FeedAdapter adapter;

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        // Firebase Data Source || No List Array needed

//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("Foods")
//                .orderByChild("timeStamp")
        FirebaseRecyclerOptions<FeedModel> options =
                new FirebaseRecyclerOptions.Builder<FeedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods"), FeedModel.class)
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