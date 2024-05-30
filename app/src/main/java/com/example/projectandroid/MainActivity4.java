package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {
    private ListView lstl;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        view();
data();
    }
    public void view(){
        lstl=findViewById(R.id.lst);
        queue = Volley.newRequestQueue(this);
    }


    private void data() {
        String url = "http://10.0.2.2:80/Android/getAllBarndsCars.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Brands> cars = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String brand=obj.getString("Brand");
                                String logo=obj.getString("logo");
                                Brands brands=new Brands(brand,logo);
                                cars.add(brands);
                            } catch (JSONException exception) {
                                Log.d("volley_error", exception.toString());
                            }
                        }
                        BrandsAdapter adapter = new BrandsAdapter(MainActivity4.this, cars);
                        lstl.setAdapter(adapter);
                        lstl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Brands selectedItem = (Brands) parent.getItemAtPosition(position);
                                String message = selectedItem.toString();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                                intent.putExtra("data", message);
                                startActivity(intent);
                            }
                        });
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
        startActivity(intent);
        finish();
    }

}