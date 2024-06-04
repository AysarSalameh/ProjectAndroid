// CarAdapter.java
package com.example.projectandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private String firstname;
    private String lastname;
    private String email;

    public CarAdapter(Context context, List<Car> carList, String firstname, String lastname, String email) {
        this.context = context;
        this.carList = carList;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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
        holder.price.setText("$" + car.getPricePerDay() + " per day");
        holder.rating.setText(String.valueOf(car.getRating()));
        Glide.with(context).load(car.getImage()).into(holder.image);

        // Handle the Book Now button click
        holder.bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CarInformationView.class);
                intent.putExtra("car_id", car.getCarID());
                intent.putExtra("car_image_url", car.getImage()); // Pass the image URL
                intent.putExtra("car_name_year", car.getModel() + " (" + car.getYear() + ")");
                intent.putExtra("car_class", car.getCarClass());
                intent.putExtra("price_per_day", car.getPricePerDay());

                // Pass user information
                intent.putExtra("stordfname", firstname);
                intent.putExtra("stordlname", lastname);
                intent.putExtra("email", email);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        TextView model, year, seats, doors, price, rating;
        ImageView image;
        Button bookNowButton;

        public CarViewHolder(View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.car_model);
            year = itemView.findViewById(R.id.car_year);
            seats = itemView.findViewById(R.id.car_seats);
            doors = itemView.findViewById(R.id.car_doors);
            price = itemView.findViewById(R.id.car_price);
            rating = itemView.findViewById(R.id.car_rating);
            image = itemView.findViewById(R.id.car_image);
            bookNowButton = itemView.findViewById(R.id.carInformation);
        }
    }
}
