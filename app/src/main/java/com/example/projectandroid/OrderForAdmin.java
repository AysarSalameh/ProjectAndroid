package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class OrderForAdmin extends AppCompatActivity {
    private ListView listView;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_for_admin);

        listView = findViewById(R.id.order_list);
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        listView.setAdapter(orderAdapter);
        queue = Volley.newRequestQueue(this);
        fetchOrders();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order selectedOrder = orderAdapter.getItem(position);
                Intent intent = new Intent(OrderForAdmin.this, OrderDetailsActivity.class);
                intent.putExtra("orderID", selectedOrder.getOrderID());
                intent.putExtra("carID", selectedOrder.getCarID());
                intent.putExtra("fullName", selectedOrder.getFullName());
                intent.putExtra("email", selectedOrder.getEmail());
                intent.putExtra("totalCost", selectedOrder.getTotalCost());
                intent.putExtra("carImageUrl", selectedOrder.getCarImageUrl());
                intent.putExtra("orderStatus", selectedOrder.getOrderStatus());
                startActivity(intent);
            }
        });
    }

    private void fetchOrders() {
        String url = "http://10.0.2.2:80/Android/getOrders.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        orderList.clear();
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
                                Toast.makeText(OrderForAdmin.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                            }
                        }
                        orderAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderForAdmin.this, "Error making the request: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(request);
    }
}
