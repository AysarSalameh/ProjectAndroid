package com.example.projectandroid;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class supplieradd extends AppCompatActivity {
    private Spinner spinnerBrand, spinnerModel, spinnerYear, spinnerClass, spinnerSeats, spinnerDoors, spinnerRating;
    private EditText editPricePerDay, editDescription, editFuelConsumption;
    private ImageView imgCar;
    private ArrayAdapter<String> brandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplieradd);

        // Initialize UI components
        initializeUIComponents();

        // Set up button listeners
        setupButtonListeners();
    }

    private void initializeUIComponents() {
        spinnerBrand = findViewById(R.id.spinner6);
        spinnerModel = findViewById(R.id.spinner);
        spinnerYear = findViewById(R.id.spinner2);
        spinnerClass = findViewById(R.id.spinner3);
        spinnerSeats = findViewById(R.id.spinner4);
        spinnerDoors = findViewById(R.id.spinner5);
        spinnerRating = findViewById(R.id.spinner5); // Verify this is correct and consider correcting ID duplication
        editPricePerDay = findViewById(R.id.editpriceperday);
        editDescription = findViewById(R.id.editDescription);
        editFuelConsumption = findViewById(R.id.editfuleconsumption);
        imgCar = findViewById(R.id.imgCar);

        // Setup ArrayAdapter for spinnerBrand
        brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        spinnerBrand.setAdapter(brandAdapter);
    }

    private void setupButtonListeners() {
        Button btnSubmitCar = findViewById(R.id.btnSubmitCar);


        spinnerBrand.setOnTouchListener((v, event) -> {
            if (brandAdapter.getCount() == 0) { // Load data only once to prevent unnecessary loads
                loadBrandNames();
            }
            return false; // Event not consumed
        });
    }

    private void loadBrandNames() {
        new Thread(() -> {
            String urlString = "http://10.0.2.2:80/Android/get_brands.php"; // URL to your PHP server
            String response = performHttpGet(urlString);
            runOnUiThread(() -> {
                if (response != null && !response.isEmpty()) {
                    ArrayList<String> brands = parseBrandResponse(response);
                    brandAdapter.clear();
                    brandAdapter.addAll(brands);
                    brandAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to load brand data", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    // Implementing a simple HTTP GET request
    private String performHttpGet(String requestURL) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    // Example JSON parser, adjust according to your actual response format
    private ArrayList<String> parseBrandResponse(String jsonResponse) {
        ArrayList<String> brandList = new ArrayList<>();
        // Parse JSON and add brands to brandList
        return brandList;
    }
}
