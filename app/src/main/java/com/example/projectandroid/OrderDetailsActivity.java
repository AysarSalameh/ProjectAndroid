package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {
    private ImageView carImage;
    private TextView customerFullName, customerEmail, orderTotalCost;
    private Button btnAccept, btnDelete;
    private int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        carImage = findViewById(R.id.car_image);
        customerFullName = findViewById(R.id.customer_full_name);
        customerEmail = findViewById(R.id.customer_email);
        orderTotalCost = findViewById(R.id.order_total_cost);
        btnAccept = findViewById(R.id.btn_accept);
        btnDelete = findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        orderID = intent.getIntExtra("orderID", -1);
        String fullName = intent.getStringExtra("fullName");
        String email = intent.getStringExtra("email");
        double totalCost = intent.getDoubleExtra("totalCost", 0.0);
        String carImageUrl = intent.getStringExtra("carImageUrl");

        if (fullName != null && email != null) {
            displayOrderDetails(fullName, email, totalCost, carImageUrl);
        } else {
            Toast.makeText(this, "Order data is not available.", Toast.LENGTH_SHORT).show();
        }

        btnAccept.setOnClickListener(v -> {
            // Implementation of accept functionality
            Toast.makeText(this, "Order Accepted", Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(v -> deleteOrder(orderID));
    }

    private void displayOrderDetails(String fullName, String email, double totalCost, String carImageUrl) {
        customerFullName.setText(fullName);
        customerEmail.setText("Email: " + email);
        orderTotalCost.setText("Total Cost: $" + totalCost);
        if (carImageUrl != null && !carImageUrl.isEmpty()) {
            Glide.with(this).load(carImageUrl).into(carImage);
        }
    }

    private void deleteOrder(int orderID) {
        String url = "http://10.0.2.2:80/Android/deleteOrder.txt";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            if (status.equals("success")) {
                                Toast.makeText(OrderDetailsActivity.this, "Order Deleted Successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Close the activity after deletion
                            } else {
                                Toast.makeText(OrderDetailsActivity.this, "Failed to Delete Order", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OrderDetailsActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderDetailsActivity.this, "Error making request: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("orderID", String.valueOf(orderID));
                return params;
            }
        };

        queue.add(postRequest);
    }
}
