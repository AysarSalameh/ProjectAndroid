package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChoosseCar extends AppCompatActivity {
private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choossecar);
        Intent intent = getIntent();
        view();
        String brand = intent.getStringExtra("data");
        txt.setText(brand);
    }
    public  void view (){
        txt = findViewById(R.id.txt);

    }
}