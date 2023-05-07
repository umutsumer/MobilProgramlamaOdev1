package com.yildiz.mezunapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText name,surname,email,password,startDate,endDate;
    Button registerButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        registerButton = findViewById(R.id.createUserButton);
        name = findViewById(R.id.editTextName);
        surname =  findViewById(R.id.editTextSurname);
        email =  findViewById(R.id.editTextEmailAddress);
        password =  findViewById(R.id.editTextPassword);
        startDate = findViewById(R.id.editDateGiris);
        endDate = findViewById(R.id.editDateMezun);
        progressBar = findViewById(R.id.registerProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    FirebaseUser user = auth.getCurrentUser();
                    String userEmail = user.getEmail();
                    String uid = user.getUid();
                    HashMap<Object,String> hashMap = new HashMap<>();
                   // HashMap<Object,String> hashMapJ = new HashMap<>();
                    hashMap.put("email",userEmail);
                    hashMap.put("uid",uid);
                    hashMap.put("name",name.getText().toString());
                    hashMap.put("surname",surname.getText().toString());
                    hashMap.put("image","");
                    hashMap.put("startTime","");
                    hashMap.put("endTime","");
                    hashMap.put("phone","");
                    hashMap.put("educationType","");
                    hashMap.put("jobInfo","");
                    hashMap.put("girisTarihi","");
                    hashMap.put("mezuniyetTarihi","");

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://mezunapp-5b548-default-rtdb.europe-west1.firebasedatabase.app");
                    DatabaseReference reference = database.getReference("Users");
                    reference.child(uid).setValue(hashMap);
                    Toast.makeText(getApplicationContext(),"Başarıyla kayıt oldunuz.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    view.getContext().startActivity(intent);
                    finish();
                }else{
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        email.setError("Bu e-posta ile ilişkilendirilmiş bir hesap var.");
                        email.requestFocus();
                    }
                }
            });
        });
    }
}
