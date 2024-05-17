package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Suppliers extends AppCompatActivity {
    private TextView txtShowName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);
        Intent intent = getIntent();
        view();
        String firstname = intent.getStringExtra("stordfname");
        String lastname = intent.getStringExtra("stordlname");
        txtShowName.setText("Welcome Suppliers "+firstname+" "+lastname);

    }
    public  void view (){
        txtShowName = findViewById(R.id.username);

    }
}