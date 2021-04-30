package com.example.macro_counter.fragments;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.macro_counter.Food;
import com.example.macro_counter.FoodAdapter;
import com.example.macro_counter.MainActivity;
import com.example.macro_counter.NewDetailActivity;
import com.example.macro_counter.R;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchFragment extends Fragment implements View.OnClickListener {

    public static final String FOOD_DATABASE_URL = "https://api.edamam.com/api/food-database/v2/parser?ingr=red%20apple&app_id=ef0fa9fe&app_key=32407434c5090649349047ef7e8a4255";
    public static final String TAG = "SearchFragment";
    private Button btnCustomItem;
    private RecyclerView rvFoods;
    private FoodAdapter foodAdapter;
    List<Food> foodList;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        rvFoods = view.findViewById(R.id.rvFoods);
        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(getContext(), foodList);

        //Steps to use the recycler view:
        //O. create layout for one row in the list
        //1. create the adapter
        //2. create the data source
        //3. set the adapter on the recycler view
        rvFoods.setAdapter(foodAdapter);
        //4. set the layout manager on the recycler view
        rvFoods.setLayoutManager(new LinearLayoutManager(getContext()));
        parseJSON();
//        Log.i(TAG, "Food0ID:" + foodList.get(1).itemName);
//        foodAdapter.notifyDataSetChanged();




        btnCustomItem = view.findViewById(R.id.btnCustomItem);
        btnCustomItem.setOnClickListener(this);
    }

    private void parseJSON() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(FOOD_DATABASE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray hints = jsonObject.getJSONArray("hints");
//                    JSONObject results = hints.getJSONObject(0);
//                    JSONObject food = results.getJSONObject("food");
                    Log.i(TAG, "Hints: " + hints.toString());
                    foodList.addAll(Food.fromJsonArray(hints));
                    foodAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Foods:" + foodList.size());
                    Log.i(TAG, "Food0ID:" + foodList.get(1).itemName);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnCustomItem:
                Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                startActivity(intent);
                break;
        }
    }
}

//Johan: 4/28 2:14pm Stopped at 13:23 on "Flixster: Episode 2 - Networking and Parsing JSON", stopped at 12:32 on "Instagram Ep 10 - Posts RecyclerView" and stopped at 2:36 at "RecyclerView + JSON Parsing - Part 4"