package com.example.projectandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.BreakIterator;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;

    public CarAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_card, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.model.setText(car.getModel());
        holder.year.setText(car.getYear() + " • " + car.getCarClass());
        holder.seats.setText(String.valueOf(car.getSeats()));
        holder.doors.setText(String.valueOf(car.getDoors()));
        holder.price.setText("$" + car.getPricePerDay());
        holder.rating.setText(String.valueOf(car.getRating()));
        Glide.with(context).load(car.getImage()).into(holder.image);
    }




    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        public View carInformation;
        public BreakIterator carModel;
        TextView model, year, seats, doors, price, rating;
        ImageView image;

        public CarViewHolder(View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.car_model);
            year = itemView.findViewById(R.id.car_year);
            seats = itemView.findViewById(R.id.car_seats);
            doors = itemView.findViewById(R.id.car_doors);
            price = itemView.findViewById(R.id.car_price);
            rating = itemView.findViewById(R.id.car_rating);
            image = itemView.findViewById(R.id.car_image);
        }
    }
}
