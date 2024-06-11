package com.example.projectandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.List;

public class CarAdapterd extends RecyclerView.Adapter<CarAdapterd.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private String firstName, lastName, email;
    private RequestQueue queue; // Initialize here

    public CarAdapterd(Context context, List<Car> carList, String firstName, String lastName, String email) {
        this.context = context;
        this.carList = carList;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.queue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_delete, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.model.setText(car.getModel());
        holder.year.setText(car.getYear() + " • " + car.getCarClass());
        holder.seats.setText(String.valueOf(car.getSeats()));
        holder.doors.setText(String.valueOf(car.getDoors()));
        Glide.with(context).load(car.getImage()).into(holder.image);

        holder.bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int carId = carList.get(holder.getAdapterPosition()).getCarID();

                String deleteUrl = "http://10.0.2.2:80/Android/delete.php?carID=" + carId;

                StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, deleteUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                carList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), carList.size());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Delete Car", "Error deleting car: " + error.getMessage());
                            }
                        });

                queue.add(deleteRequest);
            }
        });
    }


    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        TextView model, year, seats, doors, rating;
        ImageView image;
        Button bookNowButton;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.car_model);
            year = itemView.findViewById(R.id.car_year);
            seats = itemView.findViewById(R.id.car_seats);
            doors = itemView.findViewById(R.id.car_doors);
            image = itemView.findViewById(R.id.car_image);
            bookNowButton = itemView.findViewById(R.id.carInformation);
        }
    }
}
