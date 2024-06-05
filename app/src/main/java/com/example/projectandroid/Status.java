// StatusActivity.java
package com.example.projectandroid;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Status extends AppCompatActivity {

    private ListView orderListView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        orderListView = findViewById(R.id.order_list);
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        orderListView.setAdapter(orderAdapter);

        queue = Volley.newRequestQueue(this);
        fetchOrders();
    }

    private void fetchOrders() {
        String url = "http://10.0.2.2:80/Android/getOrders.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Order order = new Order(
                                        obj.getInt("orderID"),
                                        obj.getInt("carID"),
                                        obj.getString("fullName"),
                                        obj.getString("email"),
                                        obj.getDouble("totalCost"),
                                        obj.getString("carImageUrl"),
                                        obj.getString("orderStatus")
                                );
                                orderList.add(order);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        orderAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Status.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
