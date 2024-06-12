// RentalSummaryActivity.java
package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

public class RentalSummaryActivity extends AppCompatActivity {

    private TextView tv_full_name, tv_email, tv_total_cost, tv_car_name_year, tv_car_class;
    private ImageView car_image;
    private Button btn_confirm;
    private RequestQueue queue;
    private String carId, carImageUrl, fullName, email, carNameYear, carClass;
    private double totalCost;

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

        queue = Volley.newRequestQueue(this);

        // Get the intent data
        Intent intent = getIntent();
        fullName = intent.getStringExtra("stordfname") + " " + intent.getStringExtra("stordlname");
        email = intent.getStringExtra("email");
        totalCost = intent.getDoubleExtra("total_cost", 0.0);
        carNameYear = intent.getStringExtra("car_name_year");
        carClass = intent.getStringExtra("car_class");
        carImageUrl = intent.getStringExtra("car_image_url");
        carId = String.valueOf(intent.getIntExtra("car_id", -1));

        // Set the values to the views
        tv_full_name.setText(fullName);
        tv_email.setText(email);
        tv_total_cost.setText("Total Cost: $" + totalCost);
        tv_car_name_year.setText(carNameYear);
        tv_car_class.setText(carClass);
        Glide.with(this).load(carImageUrl).into(car_image);

        // Set up the confirm button click listener
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrder();
            }
        });
    }

    private void insertOrder() {
        String url = "http://10.0.2.2:80/Android/insertOrder.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(RentalSummaryActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                            // Navigate to Status Activity
                            Intent intent = new Intent(RentalSummaryActivity.this, Rental_or_stauts.class);
                            intent.putExtra("email", email); // email is retrieved from the intent extras or elsewhere in your activity
                            startActivity(intent);
                        } else {
                            Toast.makeText(RentalSummaryActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RentalSummaryActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("carID", carId);
                params.put("fullName", fullName);
                params.put("email", email);
                params.put("totalCost", String.valueOf(totalCost));
                params.put("carImageUrl", carImageUrl);
                params.put("orderStatus", "Pending");
                return params;
            }
        };

        queue.add(postRequest);
    }
}
