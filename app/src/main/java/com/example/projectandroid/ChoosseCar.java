package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ChoosseCar extends AppCompatActivity {
private TextView txt;
    String email;
    String lastname;
    String firstname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choossecar);
        Intent intent = getIntent();
        view();
       pass(intent);
        String brand = intent.getStringExtra("data");

        txt.setText(brand);
    }
    public  void view (){
        txt = findViewById(R.id.txt);

    }
    public void pass( Intent intent){
         firstname = intent.getStringExtra("stordfname");
         lastname = intent.getStringExtra("stordlname");
         email = intent.getStringExtra("email");
    }
}