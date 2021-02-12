package com.example.covofficial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn,createnewAccount_btn;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLoggedIn();

        email=findViewById(R.id.email_login_edittxt);
        password=findViewById(R.id.password_login_edittxt);
        loginBtn=findViewById(R.id.login_btn);
        createnewAccount_btn=findViewById(R.id.createNewAccount_btn);

        firebaseAuth=FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, R.string.emailAccount_toast, Toast.LENGTH_SHORT).show();
                else if(password.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, R.string.passowrd_t_toast, Toast.LENGTH_SHORT).show();

                else{

                    final String user_email = email.getText().toString();
                    final String user_password = password.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(user_email+"@covid.com", user_password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, R.string.singin_toast, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                startActivity(new Intent(MainActivity.this,MainPageActivity.class));
                                finish();
                            }
                        }});
                }

                         }

            }
        );

        createnewAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                finish();
            }
        });


    }
   //fun to check if user is logedin
  public  void isLoggedIn(){

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            startActivity(new Intent(MainActivity.this,MainPageActivity.class));
            finish();

        }
  }
}