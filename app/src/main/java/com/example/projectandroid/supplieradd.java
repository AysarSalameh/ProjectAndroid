package com.example.projectandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private static final int REQUEST_PERMISSION = 200;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Spinner spinnerBrand, spinnerYear, spinnerClass, spinnerSeats, spinnerDoors, spinnerRating, spinner6;
    private EditText editPricePerDay, editDescription, editFuelConsumption,editcarModel,editAddImage;

    private Button btnSubmitCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplieradd);

        initializeUIComponents();
        setupButtonListeners();
    }

    private void initializeUIComponents() {
        spinnerBrand = findViewById(R.id.spinner6);

        spinnerYear = findViewById(R.id.spinner);
        spinnerClass = findViewById(R.id.spinner2);
        spinnerSeats = findViewById(R.id.spinner3);
        spinnerDoors = findViewById(R.id.spinner4);
        spinnerRating = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);

        editcarModel = findViewById(R.id.editcarModel);
        editPricePerDay = findViewById(R.id.editpriceperday);
        editDescription = findViewById(R.id.editDescription);
        editFuelConsumption = findViewById(R.id.editfuleconsumption);
        editAddImage = findViewById(R.id.editAddImage);
        btnSubmitCar = findViewById(R.id.btnSubmitCar);


        initializeSpinners();
    }

    private void initializeSpinners() {

        setSpinnerData(spinnerYear, generateYears());
        setSpinnerData(spinnerClass, new String[]{"Sport", "Sport SUV"});
        setSpinnerData(spinnerSeats, new String[]{"2", "4", "6"});
        setSpinnerData(spinnerDoors, new String[]{"2", "4"});
        setSpinnerData(spinnerRating, new String[]{"1", "2", "3", "4", "5"});
        setSpinnerData(spinner6, generateNumbersFrom0To15());
    }

    private String[] generateYears() {
        String[] years = new String[75];
        for (int i = 0; i < 75; i++) {
            years[i] = Integer.toString(1950 + i);
        }
        return years;
    }

    private String[] generateNumbersFrom0To15() {
        String[] numbers = new String[16];
        for (int i = 0; i < 16; i++) {
            numbers[i] = Integer.toString(i);
        }
        return numbers;
    }

    private void setSpinnerData(Spinner spinner, String[] data) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        btnSubmitCar.setOnClickListener(v -> submitCarData());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

        }
    }

    private void submitCarData() {
        new Thread(() -> {
            String urlString = "http://10.0.2.2:80/Android/add_car.php";
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("brandID", spinnerBrand.getSelectedItem().toString());
            postDataParams.put("model", editcarModel.getText().toString());
            postDataParams.put("year", spinnerYear.getSelectedItem().toString());
            postDataParams.put("carClass", spinnerClass.getSelectedItem().toString());
            postDataParams.put("seats", spinnerSeats.getSelectedItem().toString());
            postDataParams.put("doors", spinnerDoors.getSelectedItem().toString());
            postDataParams.put("pricePerDay", editPricePerDay.getText().toString());
            postDataParams.put("rating", spinnerRating.getSelectedItem().toString());
            postDataParams.put("description", editDescription.getText().toString());
            postDataParams.put("fuelConsumption", editFuelConsumption.getText().toString());
            postDataParams.put("image", editAddImage.getText().toString());  // Make sure this is the correct EditText for the image URL

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

    private String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
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
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
