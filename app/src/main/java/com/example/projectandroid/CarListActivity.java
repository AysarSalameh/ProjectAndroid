package com.example.projectandroid;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CarListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;
    private RequestQueue queue;
    private String brandID;
    private String brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carList = new ArrayList<>();
        carAdapter = new CarAdapter(this, carList);
        recyclerView.setAdapter(carAdapter);


        queue = Volley.newRequestQueue(this);

        brandID = getIntent().getStringExtra("brandID");
        brandName = getIntent().getStringExtra("brandName");


        fetchCars();
    }

    private void fetchCars() {
        String url = "http://10.0.2.2:80/Android/getCarsByBrand.php?brandID=" + brandID;

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
