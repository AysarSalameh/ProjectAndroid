// OrderAdapter.java
package com.example.projectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(@NonNull Context context, @NonNull List<Order> orderList) {
        super(context, 0, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_order, parent, false);
        }

        Order order = orderList.get(position);

        TextView fullName = listItem.findViewById(R.id.full_name);
        TextView carName = listItem.findViewById(R.id.car_name);
        TextView orderStatus = listItem.findViewById(R.id.order_status);
        ImageView carImage = listItem.findViewById(R.id.car_image);

        fullName.setText(order.getFullName());
        carName.setText(order.getCarID() + "");  // You might want to replace this with a car model or name if available
        orderStatus.setText(order.getOrderStatus());
        Glide.with(context).load(order.getCarImageUrl()).into(carImage);

        return listItem;
    }
}
