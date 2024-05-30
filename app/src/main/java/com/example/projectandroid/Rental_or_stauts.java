package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rental_or_stauts extends AppCompatActivity {
    private TextView txtShowName;
    private Button car;
    private Button status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_or_stauts);
        Intent intent = getIntent();
        view();
        String firstname = intent.getStringExtra("stordfname");
        String lastname = intent.getStringExtra("stordlname");
        txtShowName.setText("Welcome "+firstname+" "+lastname);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rental_or_stauts.this, Brand.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public  void view (){
        txtShowName = findViewById(R.id.username);
        car=findViewById(R.id.car);
        status=findViewById(R.id.status);
    }
}