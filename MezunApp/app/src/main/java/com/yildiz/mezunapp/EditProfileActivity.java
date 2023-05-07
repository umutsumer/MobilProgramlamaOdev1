package com.yildiz.mezunapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    EditText phoneNr, girisDate, mezunDate,oldPass,newPass,newPassAgain;
    TextView nameTxt,surnameTxt,emailTxt;
    Button degisiklikKaydet,sifreKaydet;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useredit_page);
        phoneNr = findViewById(R.id.editTextPhoneNr);
        girisDate =findViewById(R.id.editDateGiris);
        mezunDate =findViewById(R.id.editDateMezun);
        oldPass = findViewById(R.id.editPasswordOldPass);
        newPass =findViewById(R.id.editPasswordNewPass);
        newPassAgain = findViewById(R.id.editPasswordNewPassAgain);
        degisiklikKaydet =  findViewById(R.id.saveChangesButton);
        sifreKaydet = findViewById(R.id.savePasswordButton);
        emailTxt = findViewById(R.id.TextEmail);
        nameTxt = findViewById(R.id.TextName);
        surnameTxt = findViewById(R.id.TextSurname);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mezunapp-5b548-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference("Users");
        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name,surname,phoneNum,email,girisTar,mezunTar;
                for(DataSnapshot ds: snapshot.getChildren()){

                    name = ""+ds.child("name").getValue();
                    surname =""+ds.child("surname").getValue();
                    phoneNum = ""+ds.child("phone").getValue();
                    email = ""+ds.child("email").getValue();
                    girisTar = ""+ds.child("girisTarihi").getValue();
                    mezunTar = ""+ds.child("mezuniyetTarihi").getValue();
                    nameTxt.setText(name);
                    surnameTxt.setText(surname);
                    emailTxt.setText(email);
                    phoneNr.setText(phoneNum);
                    girisDate.setText(girisTar);
                    mezunDate.setText(mezunTar);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        degisiklikKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = firebaseAuth.getCurrentUser().getUid();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("phone",phoneNr.getText().toString());
                hashMap.put("girisTarihi",girisDate.getText().toString());
                hashMap.put("mezuniyetTarihi",mezunDate.getText().toString());
                databaseReference.child(uid).updateChildren(hashMap);

            }
        });

    sifreKaydet.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    });

    }


}
