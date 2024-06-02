// CarInformationView.java
package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class CarInformationView extends AppCompatActivity {

    private ImageView carImage;
    private TextView carNameYear, carClass, carPricePerDay, carDescription;
    private TextView carFuelConsumption, carSeats, carDoors, tvRentalDays;
    private Button btnDecreaseDays, btnIncreaseDays, btnBookNow;
    private int rentalDays = 1; // Default rental days
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_information_view);

        // Initialize the views
        carImage = findViewById(R.id.car_image);
        carNameYear = findViewById(R.id.car_name_year);
        carClass = findViewById(R.id.car_class);
        carPricePerDay = findViewById(R.id.car_price_per_day);
        carDescription = findViewById(R.id.car_description);
        carFuelConsumption = findViewById(R.id.car_fuel_consumption);
        carSeats = findViewById(R.id.car_seats);
        carDoors = findViewById(R.id.car_doors);
        tvRentalDays = findViewById(R.id.tv_rental_days);
        btnDecreaseDays = findViewById(R.id.btn_decrease_days);
        btnIncreaseDays = findViewById(R.id.btn_increase_days);
        btnBookNow = findViewById(R.id.btn_book_now);

        queue = Volley.newRequestQueue(this);

        // Get the intent and the Car ID
        Intent intent = getIntent();
        int carId = intent.getIntExtra("car_id", -1);

        // Fetch car details from the database
        fetchCarDetails(carId);

        // Set up button click listeners for increasing and decreasing rental days
        btnDecreaseDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rentalDays > 1) {
                    rentalDays--;
                    tvRentalDays.setText(String.valueOf(rentalDays));
                }
            }
        });

        btnIncreaseDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentalDays++;
                tvRentalDays.setText(String.valueOf(rentalDays));
            }
        });

        // Set up button click listener for booking the car
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void fetchCarDetails(int carId) {
        String url = "http://10.0.2.2:80/Android/getCarDetails.php?carID=" + carId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String model = response.getString("model");
                            int year = response.getInt("year");
                            String carClassText = response.getString("carClass");
                            double pricePerDay = response.getDouble("pricePerDay");
                            String description = response.getString("description");
                            String fuelConsumption = response.getString("fuelConsumption");
                            int seats = response.getInt("seats");
                            int doors = response.getInt("doors");
                            String imageUrl = response.getString("image");

                            carNameYear.setText(model + " (" + year + ")");
                            carClass.setText(carClassText);
                            carPricePerDay.setText("$" + pricePerDay + " per day");
                            carDescription.setText(description);
                            carFuelConsumption.setText(fuelConsumption);
                            carSeats.setText(String.valueOf(seats));
                            carDoors.setText(String.valueOf(doors));

                            Glide.with(CarInformationView.this).load(imageUrl).into(carImage);
                        } catch (JSONException e) {
                            Log.e("CarInformationView", "JSON parsing error", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CarInformationView", "Volley error", error);
            }
        });

        queue.add(request);
    }
}
