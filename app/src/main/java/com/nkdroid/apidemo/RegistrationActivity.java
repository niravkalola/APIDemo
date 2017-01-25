package com.nkdroid.apidemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.nkdroid.apidemo.custom.PostServiceCall;
import com.nkdroid.apidemo.custom.PrefUtils;
import com.nkdroid.apidemo.model.AppConstants;
import com.nkdroid.apidemo.model.User;

import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnLogin,btnRegistration;
    private EditText etEmail,etPassword,etcPassword,etMobile;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etMobile= (EditText) findViewById(R.id.etMobile);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        etcPassword= (EditText) findViewById(R.id.etCPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnRegistration= (Button) findViewById(R.id.btnRegistration);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etEmail.getText().toString().trim().length()==0){
                    Toast.makeText(RegistrationActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
                }else if(!isEmailValid(etEmail.getText().toString().trim())){
                    Toast.makeText(RegistrationActivity.this,"Please enter validemail",Toast.LENGTH_LONG).show();
                }else if(etPassword.getText().toString().trim().length()==0){
                    Toast.makeText(RegistrationActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                }else if(etcPassword.getText().toString().trim().length()==0){
                    Toast.makeText(RegistrationActivity.this,"Please enter confirm password",Toast.LENGTH_LONG).show();
                }else if(!etPassword.getText().toString().trim().equals(etcPassword.getText().toString().trim())){
                    Toast.makeText(RegistrationActivity.this,"password must match",Toast.LENGTH_LONG).show();
                }else if(etMobile.getText().toString().trim().length()==0){
                    Toast.makeText(RegistrationActivity.this,"Please enter mobile",Toast.LENGTH_LONG).show();
                }else {
                    progressDialog=new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setMessage("Submitting...");
                    progressDialog.show();
                    JSONObject requestObject=new JSONObject();
                    try {
                        requestObject.put("mobile", etMobile.getText().toString() + "");
                        requestObject.put("email", etEmail.getText().toString() + "");
                        requestObject.put("password", etPassword.getText().toString() + "");

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    new PostServiceCall(AppConstants.REGISTRATION, requestObject) {

                        @Override
                        public void response(String response) {

                            progressDialog.dismiss();
                            User user = new GsonBuilder().create().fromJson(response, User.class);

                            if(user.getResponseId().equalsIgnoreCase("1")){
                                PrefUtils.setUser(user,RegistrationActivity.this);
                                Toast.makeText(RegistrationActivity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                                Intent i=new Intent(RegistrationActivity.this,HomeActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(RegistrationActivity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }


                        @Override
                        public void error(String error) {

                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this,"Error while submitting data, try again later!",Toast.LENGTH_LONG).show();
                        }

                    }.call();


                }

            }
        });
    }


    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
