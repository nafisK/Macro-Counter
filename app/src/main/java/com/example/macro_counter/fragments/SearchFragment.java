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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.macro_counter.CustomFoodSearchActivity;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.macro_counter.Food;
import com.example.macro_counter.FoodAdapter;
import com.example.macro_counter.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchFragment extends Fragment implements View.OnClickListener {

    public static final String FOOD_DATABASE_URL = "https://api.edamam.com/api/food-database/v2/parser?ingr=%20&app_id=ef0fa9fe&app_key=32407434c5090649349047ef7e8a4255";
    public static final String TAG = "SearchFragment";
    private Button btnCustomItem;
    private RecyclerView rvFoods;
    private FoodAdapter foodAdapter;
    private Button btSearchAPI;
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
        rvFoods = view.findViewById(R.id.rvCustomFoods);

        EditText editText = view.findViewById(R.id.edit_text);
        btSearchAPI = view.findViewById(R.id.btnSearchAPI);

        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(getContext(), foodList);

        rvFoods.setAdapter(foodAdapter);

        //4. set the layout manager on the recycler view
        rvFoods.setLayoutManager(new LinearLayoutManager(getContext()));


        btSearchAPI.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //Retrieves input of edit text
                        String searchValue = editText.getText().toString();
                        //Calls parseJSON function with the input of edit text replacing the %s in the url
                        parseJSON(searchValue);
                        editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    }
                });


        btnCustomItem = view.findViewById(R.id.btnNewFood);
        btnCustomItem.setOnClickListener(this);
    }


    //Retrieves JSON data
    private void parseJSON(String text) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getUrl(text), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray hints = jsonObject.getJSONArray("hints");
                    Log.i(TAG, "Hints: " + hints.toString());
                    foodList.clear();
                    foodList.addAll(Food.fromJsonArray(hints));
                    foodAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Foods:" + foodList.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
                Toast.makeText(getContext(), "Type something!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnNewFood:
                Intent intent = new Intent(getActivity(), CustomFoodSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    //Replaces the %s in the url to the string value that the user inputs into the edit text
    public String getUrl(String search) {
        return String.format("https://api.edamam.com/api/food-database/v2/parser?ingr=%s&app_id=ef0fa9fe&app_key=32407434c5090649349047ef7e8a4255", search);
    }
}

//Johan: 4/28 2:14pm Stopped at 13:23 on "Flixster: Episode 2 - Networking and Parsing JSON", stopped at 12:32 on "Instagram Ep 10 - Posts RecyclerView" and stopped at 2:36 at "RecyclerView + JSON Parsing - Part 4"