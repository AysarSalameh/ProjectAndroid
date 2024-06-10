package com.example.projectandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class supplieradd extends AppCompatActivity {
    private Spinner spinnerBrand, spinnerModel, spinnerYear, spinnerClass, spinnerSeats, spinnerDoors, spinnerRating;
    private EditText editPricePerDay, editDescription, editFuelConsumption;
    private ImageView imgCar;

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
        spinnerRating = findViewById(R.id.spinner5); // Make sure to adjust if this is incorrect
        editPricePerDay = findViewById(R.id.editpriceperday);
        editDescription = findViewById(R.id.editDescription);
        editFuelConsumption = findViewById(R.id.editfuleconsumption);
        imgCar = findViewById(R.id.imgCar);
    }

    private void setupButtonListeners() {
        Button btnSubmitCar = findViewById(R.id.btnSubmitCar);
        btnSubmitCar.setOnClickListener(v -> submitCarData());
    }

    private void submitCarData() {
        new Thread(() -> {
            String urlString = "http://10.0.2.2:80/Android/add_car.php"; // URL to your PHP server
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("brandID", spinnerBrand.getSelectedItem().toString());
            postDataParams.put("model", spinnerModel.getSelectedItem().toString());
            postDataParams.put("year", spinnerYear.getSelectedItem().toString());
            postDataParams.put("carClass", spinnerClass.getSelectedItem().toString());
            postDataParams.put("seats", spinnerSeats.getSelectedItem().toString());
            postDataParams.put("doors", spinnerDoors.getSelectedItem().toString());
            postDataParams.put("pricePerDay", editPricePerDay.getText().toString());
            postDataParams.put("rating", spinnerRating.getSelectedItem().toString());
            postDataParams.put("description", editDescription.getText().toString());
            postDataParams.put("fuelConsumption", editFuelConsumption.getText().toString());

            String response = performPostCall(urlString, postDataParams);
            runOnUiThread(() -> {
                if (response != null && !response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Response was empty", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }



    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        StringBuilder response = new StringBuilder();
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            } else {
                response.append("Error Registering");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}