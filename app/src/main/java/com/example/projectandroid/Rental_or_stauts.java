package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rental_or_stauts extends AppCompatActivity {
    private TextView txtShowName;
    private Button car;
    private Button status;
    private String firstname;
    private String lastname;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_or_stauts);

        view();

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            firstname = intent.getStringExtra("stordfname");
            lastname = intent.getStringExtra("stordlname");
            email = intent.getStringExtra("email");
        } else {
            firstname = savedInstanceState.getString("stordfname");
            lastname = savedInstanceState.getString("stordlname");
            email = savedInstanceState.getString("email");
        }

        txtShowName.setText("Welcome," + firstname);

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rental_or_stauts.this, Brand.class);
                intent.putExtra("stordfname", firstname);
                intent.putExtra("stordlname", lastname);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rental_or_stauts.this, Status.class);
                intent.putExtra("stordfname", firstname);
                intent.putExtra("stordlname", lastname);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });
    }

    public void view() {
        txtShowName = findViewById(R.id.username);
        car = findViewById(R.id.car);
        status = findViewById(R.id.status);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("stordfname", firstname);
        outState.putString("stordlname", lastname);
        outState.putString("email", email);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = getIntent();
        firstname = intent.getStringExtra("stordfname");
        lastname = intent.getStringExtra("stordlname");
        email = intent.getStringExtra("email");
        txtShowName.setText("Welcome," + firstname);
    }
}
