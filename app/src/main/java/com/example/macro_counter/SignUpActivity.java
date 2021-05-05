package com.example.macro_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText etEmail, etPassword, etName, etAge, etWeight, etHeightCm, etHeightFt, etHeightIn;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private Spinner spinActivity;
    private String activity;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeightCm = findViewById(R.id.etHeightCm);
        etHeightFt = findViewById(R.id.etHeightFt);
        etHeightIn = findViewById(R.id.etHeightIn);

        progressBar = findViewById(R.id.progressBar);
        spinActivity = findViewById(R.id.spinActivity);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SignUpActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinActivityString));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinActivity.setAdapter(myAdapter);
        spinActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        activity = "sedentary";
                        break;
                    case 1:
                        activity = "light";
                        break;
                    case 2:
                        activity = "moderate";
                        break;
                    case 3:
                        activity = "hard";
                        break;
                    case 4:
                        activity = "very hard";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btnSignUp:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
//        String activity = spinActivity.toString();
        String weight = etWeight.getText().toString().trim();
        String heightCm = etHeightCm.getText().toString().trim();
        String heightFt = etHeightFt.getText().toString().trim();
        String heightIn = etHeightIn.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide a valid email!");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassword.setError("Password needs to be greater than 6 characters!");
            etPassword.requestFocus();
            return;
        }

        if(name.isEmpty()){
            etName.setError("Name is required!");
            etName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            etName.setError("Age is required!");
            etName.requestFocus();
            return;
        }

        if(weight.isEmpty()){
            etWeight.setError("Weight is required!");
            etWeight.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email, name, password, weight, heightFt, heightIn, heightCm, age, activity);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User has been registered Successfully!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        finish();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(SignUpActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}