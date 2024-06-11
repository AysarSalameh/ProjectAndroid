package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class suppliermin extends AppCompatActivity {
Button stat;
Button delete;
    String email;
    String lastname;
    String firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suppliermin);
        Intent intent3=getIntent();
        pass(intent3);
        stat=(Button)findViewById(R.id.button5);
        delete=findViewById(R.id.button7);
        Intent intent=new Intent(suppliermin.this, supplieradd.class);
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(suppliermin.this, DeleteCar.class);
                startActivity(intent);
            }
        });
    }
    public void pass( Intent intent){
        firstname = intent.getStringExtra("stordfname");
        lastname = intent.getStringExtra("stordlname");
        email = intent.getStringExtra("email");
        String message = "First Name: " + firstname + "\n" +
                "Last Name: " + lastname + "\n" +
                "Email: " + email;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}