// RentalSummaryActivity.java
package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class RentalSummaryActivity extends AppCompatActivity {

    private TextView tv_full_name, tv_email, tv_total_cost, tv_car_name_year, tv_car_class;
    private ImageView car_image;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_summary);

        // Initialize the views
        tv_full_name = findViewById(R.id.tv_full_name);
        tv_email = findViewById(R.id.tv_email);
        tv_total_cost = findViewById(R.id.tv_total_cost);
        tv_car_name_year = findViewById(R.id.tv_car_name_year);
        tv_car_class = findViewById(R.id.tv_car_class);
        car_image = findViewById(R.id.car_image);
        btn_confirm = findViewById(R.id.btn_confirm);

        // Get the intent data
        Intent intent = getIntent();
        String firstname = intent.getStringExtra("stordfname");
        String lastname = intent.getStringExtra("stordlname");
        String email = intent.getStringExtra("email");
        double totalCost = intent.getDoubleExtra("total_cost", 0.0);
        String carNameYear = intent.getStringExtra("car_name_year");
        String carClass = intent.getStringExtra("car_class");
        String carImageUrl = intent.getStringExtra("car_image_url");

        // Set the values to the views
        tv_full_name.setText(firstname + " " + lastname);
        tv_email.setText(email);
        tv_total_cost.setText("Total Cost: $" + totalCost);
        tv_car_name_year.setText(carNameYear);
        tv_car_class.setText(carClass);
        Glide.with(this).load(carImageUrl).into(car_image);

        // Set up the confirm button click listener
        btn_confirm.setOnClickListener(v -> {
            // Handle the confirmation logic here
            // For example, save the booking information to the database
        });
    }
}
