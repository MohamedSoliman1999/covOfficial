package com.example.covofficial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
    EditText name_EditTxt,
            email_EditTxt,
            password_EditTxt,
            confirmPassword_EditTxt,
            phoneRg_EditText
            , governrate_EditTxt
            ,adress_EditTxt;
     RadioButton male_Kind_Rb
            , female_Kind_Rb;
    Button regester_btn;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth=FirebaseAuth.getInstance();

        name_EditTxt=findViewById(R.id.name_Edit_txt);
        email_EditTxt=findViewById(R.id.email_Edit_txt);
        password_EditTxt=findViewById(R.id.password_Edit_txt);
        confirmPassword_EditTxt=findViewById(R.id.conFirmPassword_Edit_txt);
        phoneRg_EditText=findViewById(R.id.phoneRg_Edit_txt);
        governrate_EditTxt=findViewById(R.id.govern_Edit_txt);
        adress_EditTxt=findViewById(R.id.adress_Edit_txt);

        male_Kind_Rb=findViewById(R.id.male);
        female_Kind_Rb=findViewById(R.id.female);

        regester_btn=findViewById(R.id.registerbtn);
        regester_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /* FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            if (user!=null)
            {
                startActivity(new Intent(Main2Activity.this,MainPageActivity.class));
                finish();
                return;


            }*/

                if (name_EditTxt.getText().toString().isEmpty())
                    Toast.makeText(Main2Activity.this, R.string.toast_name, Toast.LENGTH_SHORT).show();
                else if(email_EditTxt.getText().toString().isEmpty())
                    Toast.makeText(Main2Activity.this, R.string.email_toast, Toast.LENGTH_SHORT).show();
                else if(password_EditTxt.getText().toString().isEmpty())
                    Toast.makeText(Main2Activity.this, R.string.password_toast, Toast.LENGTH_SHORT).show();
                else if(password_EditTxt.getTextSize()<8)
                    Toast.makeText(Main2Activity.this, "Enter Valid Paasword !", Toast.LENGTH_SHORT).show();
                else if(confirmPassword_EditTxt.getText().toString().isEmpty())
                    Toast.makeText(Main2Activity.this, R.string.confirmpass_toast, Toast.LENGTH_SHORT).show();
                else if(phoneRg_EditText.getText().toString().isEmpty())
                    Toast.makeText(Main2Activity.this, R.string.phone_toast, Toast.LENGTH_SHORT).show();
                else if(governrate_EditTxt.getText().toString().isEmpty())
                    Toast.makeText(Main2Activity.this, R.string.toast_govern, Toast.LENGTH_SHORT).show();
                  else if(adress_EditTxt.getText().toString().isEmpty())
                    Toast.makeText(Main2Activity.this, R.string.toast_adress, Toast.LENGTH_SHORT).show();
                else{

                    final String name=name_EditTxt.getText().toString();
                    String email=email_EditTxt.getText().toString();
                    String password=password_EditTxt.getText().toString();
                    String confirmpassword=confirmPassword_EditTxt.getText().toString();
                    final String phone_user=phoneRg_EditText.getText().toString();
                    final String governerate=governrate_EditTxt.getText().toString();
                    final String adress=adress_EditTxt.getText().toString();
                    final String kind;



                    if(male_Kind_Rb.isChecked())
                    {
                        kind = "Male";
                    }
                    else
                    {
                        kind = "Female";
                    }
                    if (password.equals(confirmpassword)) {
                        firebaseAuth.createUserWithEmailAndPassword(email+"@covid.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful())
                                {
                                    Toast.makeText(Main2Activity.this, R.string.tryanoterAcount_toast, Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    String user_id = firebaseAuth.getCurrentUser().getUid();
                                    //DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                    Patient patient=new Patient(name,phone_user,kind,governerate,adress);
                                    //String current_user= FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DatabaseReference firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                    firebaseDatabase.setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                progressDialog = new ProgressDialog(Main2Activity.this);
                                                progressDialog.setMessage(getString(R.string.messgae_progressdialog_regester));
                                                progressDialog.show();
                                                startActivity(new Intent(Main2Activity.this,MainPageActivity.class));
                                                progressDialog.dismiss();
                                                finish();

                                                return;

                                            }
                                            else
                                                Toast.makeText(Main2Activity.this, R.string.toast_internet_prob,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });

                    }
                    else
                        Toast.makeText(Main2Activity.this, R.string.confirmpassdoesMpass_Toat, Toast.LENGTH_SHORT).show();

                   }
                   // progressDialog.dismiss();






            }
        });


        }
}
