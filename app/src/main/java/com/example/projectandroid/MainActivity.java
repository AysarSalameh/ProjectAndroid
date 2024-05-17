package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {

    private Button Login;
    private Button reg;
private CheckBox chk;
    private EditText fname;
    private  EditText lname;
    private EditText email;
    private EditText pass;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("Users");
    private CollectionReference colRefsupplier = db.collection("Suppliers");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view();
        setupSharedPrefs();
        checkPrefs();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chk.isChecked()) {
                    String firstname = fname.getText().toString();
                    String lastname = lname.getText().toString();
                    String stremail = email.getText().toString();
                    String strpass = pass.getText().toString();
                    edit.putString("firstname", firstname);
                    edit.putString("lastname", lastname);
                    edit.putString("email", stremail);
                    edit.putString("password", strpass);
                    edit.commit();
                    Users user = new Users(firstname, lastname, stremail, strpass);
                    if (isInternetAvailable()) {
                        colRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Error333", e.toString());
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean flag=true;
                    String firstname = fname.getText().toString();
                    String lastname = lname.getText().toString();
                    String stremail = email.getText().toString();
                    String strpass = pass.getText().toString();
                    edit.putString("firstname", firstname);
                    edit.putString("lastname", lastname);
                    edit.putString("email", stremail);
                    edit.putString("password", strpass);
                    edit.commit();
                    Users supplier = new Users(firstname, lastname, stremail, strpass);
                    if (isInternetAvailable()) {
                        colRefsupplier.add(supplier).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                intent.putExtra("FLAG", flag);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Error333", e.toString());
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                }

        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("firstname", fname.getText().toString());
        outState.putString("lastname", lname.getText().toString());
        outState.putString("email", email.getText().toString());
        outState.putString("password", pass.getText().toString());
        outState.putBoolean("isChecked", chk.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fname.setText(savedInstanceState.getString("firstname", ""));
        lname.setText(savedInstanceState.getString("lastname", ""));
        email.setText(savedInstanceState.getString("email", ""));
        pass.setText(savedInstanceState.getString("password", ""));
        chk.setChecked(savedInstanceState.getBoolean("isChecked", false));
    }


    public void  view (){
        reg=findViewById(R.id.btnreg);
        Login = findViewById(R.id.btnlogin);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        chk=findViewById(R.id.chk);
    }


    private void setupSharedPrefs() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        edit = preferences.edit();
    }
    private void checkPrefs() {
        String firstname=preferences.getString("firstname", "");
        String lastname=preferences.getString("lastname", "");
        String password=preferences.getString("password", "");
        String emaill=preferences.getString("email", "");
        fname.setText(firstname);
        lname.setText(lastname);
        email.setText(emaill);
        pass.setText(password);
    }
    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
