package com.yildiz.mezunapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText userName,passWord;
    TextView registerText;
    FirebaseAuth auth;
    public ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        userName = findViewById(R.id.emailText);
        passWord = findViewById(R.id.passwordText);
        loginBtn = findViewById(R.id.button);
        registerText = findViewById(R.id.createUsterText);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        //setContentView(R.layout.login_page);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                    view.getContext().startActivity(intent);
                }catch (RuntimeException e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loginBtn.setEnabled(false);
                auth.signInWithEmailAndPassword(userName.getText().toString(),passWord.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Başarıyla giriş yaptınız.",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(view.getContext(), FeedActivity.class);
                                view.getContext().startActivity(intent);
                                finish();
                            }else{
                                loginBtn.setEnabled(true);
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    userName.setError("Hatalı Mail Adresi.");
                                    userName.requestFocus();
                                } catch (FirebaseAuthInvalidCredentialsException e){
                                    passWord.setError("Hatalı Şifre.");
                                    passWord.requestFocus();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                    }
                });
            }
        });

    }

    protected void onPause() {

        super.onPause();

    }
}