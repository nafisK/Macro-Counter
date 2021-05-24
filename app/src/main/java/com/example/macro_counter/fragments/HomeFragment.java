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
import android.widget.AdapterView;

import com.example.macro_counter.FeedAdapter;
import com.example.macro_counter.FeedAdapter_2;
import com.example.macro_counter.FeedModel;
import com.example.macro_counter.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private RecyclerView rvFeed;
    private DatabaseReference database;
//    FeedAdapter adapter;
    FeedAdapter_2 adapter;
    ArrayList<FeedModel> list;




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
        database = FirebaseDatabase.getInstance().getReference("Foods");
        rvFeed.setHasFixedSize(true);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();

        adapter = new FeedAdapter_2(getContext(), list);
        rvFeed.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FeedModel feedModel = dataSnapshot.getValue(FeedModel.class);
                    list.add(feedModel);

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

        // Firebase Data Source || No List Array needed

//        FirebaseRecyclerOptions<FeedModel> options =
//                new FirebaseRecyclerOptions.Builder<FeedModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods"), FeedModel.class)
//                        .build();


//        adapter = new FeedAdapter(options);
//        rvFeed.setAdapter(adapter);



    }




//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }


}