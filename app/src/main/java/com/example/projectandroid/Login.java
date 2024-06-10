package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    public static final String EMAIL = "EMAIL";
    public static final String PASS1 = "PASS";
    public static final String flag1 = "FLAG";
    private CheckBox checkBox;

    private Button Login;
    private EditText email;
    private EditText pass;
    private boolean flaag = false;
    private TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        view();
        setupSharedPrefs();
        checkPrefs();
        reg = findViewById(R.id.textView4);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.projectandroid.Login.this, Registration.class);
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailstr = email.getText().toString().trim();
                String passsrt = pass.getText().toString().trim();

                // Check if email and password are empty
                if (emailstr.isEmpty() || passsrt.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return; // Exit the method without attempting login
                }

                if (checkBox.isChecked()) {
                    if (!flaag) {
                        edit.putString(EMAIL, emailstr);
                        edit.putString(PASS1, passsrt);
                        edit.putBoolean(flag1, true);
                        edit.commit();
                    }
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users")
                        .whereEqualTo("email", emailstr)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                    String emailstord = document.getString("email");
                                    String passstord = document.getString("password");
                                    String stordfname = document.getString("firtName");
                                    String stordlname = document.getString("lastName");

                                    if (emailstr.equals(emailstord) && passsrt.equals(passstord)) {
                                        Toast.makeText(com.example.projectandroid.Login.this, "successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(com.example.projectandroid.Login.this, Rental_or_stauts.class);
                                        intent.putExtra("stordfname", stordfname);
                                        intent.putExtra("stordlname", stordlname);
                                        intent.putExtra("email", emailstord);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(com.example.projectandroid.Login.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("Suppliers")
                                            .whereEqualTo("email", emailstr)
                                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    if (!queryDocumentSnapshots.isEmpty()) {
                                                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                                        String emailstord = document.getString("email");
                                                        String passstord = document.getString("password");
                                                        String stordfname = document.getString("firtName");
                                                        String stordlname = document.getString("lastName");

                                                        if (emailstr.equals(emailstord) && passsrt.equals(passstord)) {
                                                            Toast.makeText(com.example.projectandroid.Login.this, "successful", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(com.example.projectandroid.Login.this, suppliermin.class);
                                                            intent.putExtra("stordfname", stordfname);
                                                            intent.putExtra("stordlname", stordlname);
                                                            intent.putExtra("email", emailstord);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(com.example.projectandroid.Login.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {

                                                        Toast.makeText(com.example.projectandroid.Login.this, "User does not exist", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


            }
        });
    }

    private void setupSharedPrefs() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        edit = preferences.edit();
    }

    public void view() {
        Login = findViewById(R.id.btnlogin);
        email = findViewById(R.id.email);
        checkBox = findViewById(R.id.chk);
        pass = findViewById(R.id.pass);
    }

    private void checkPrefs() {
        flaag = preferences.getBoolean(flag1, false);

        if (flaag) {
            String emailstr = preferences.getString(EMAIL, "");
            String passwordstr = preferences.getString(PASS1, "");
            email.setText(emailstr);
            pass.setText(passwordstr);
            checkBox.setChecked(true);
        }
    }
}