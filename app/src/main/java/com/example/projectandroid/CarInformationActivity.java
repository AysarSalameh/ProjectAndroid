package com.example.projectandroid;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CarInformationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_information);

        // Retrieve carID from Intent and use it to fetch car details
        int carID = getIntent().getIntExtra("carID", -1);
        // TODO: Fetch car details using carID and display them
    }
}
