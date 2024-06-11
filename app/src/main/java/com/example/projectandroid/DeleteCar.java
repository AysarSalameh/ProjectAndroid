package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeleteCar extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CarAdapterd carAdapter;
    private RequestQueue queue;
    private List<Car> carList;
    private String brandName;
    private String firstname;
    private String lastname;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_car);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        if (intent != null) {
            firstname = intent.getStringExtra("firstname");
            lastname = intent.getStringExtra("lastname");
            email = intent.getStringExtra("email");
        }
        carList = new ArrayList<>();
        carAdapter = new CarAdapterd(this, carList, firstname, lastname, email);
        recyclerView.setAdapter(carAdapter);

        // Initialize the request queue
        queue = Volley.newRequestQueue(this);

        fetchCars();
    }
    private void fetchCars() {
        String url = "http://10.0.2.2:80/Android/getAllCars.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Car car = new Car(
                                        obj.getInt("carID"),
                                        obj.getInt("brandID"),
                                        obj.getString("model"),
                                        obj.getInt("year"),
                                        obj.getString("carClass"),
                                        obj.getInt("seats"),
                                        obj.getInt("doors"),
                                        obj.getString("image"),
                                        obj.getDouble("pricePerDay"),
                                        obj.getDouble("rating")
                                );
                                carList.add(car);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        carAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Error", error.toString());
            }
        });

        queue.add(request);
    }
}