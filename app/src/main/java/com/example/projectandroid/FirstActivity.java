package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class FirstActivity extends AppCompatActivity {
private Button btn;
    private Animation bottom;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        img = findViewById(R.id.img);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);



        AnimationSet s = new AnimationSet(false);
        s.addAnimation(bottom);
        img.setAnimation(s);

        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, Registration.class);
                startActivity(intent);
            }
        });
    }
}