package com.nkdroid.apidemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin,btnRegistration;
    private EditText etEmail,etPassword;
    private ProgressDialog progressDialog;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            user=PrefUtils.getUser(LoginActivity.this);
            if(user.getUserId().length()>0){
                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        etEmail= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);

        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnRegistration= (Button) findViewById(R.id.btnRegistration);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().trim().length()==0){
                    Toast.makeText(LoginActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
                }else if(etPassword.getText().toString().trim().length()==0){
                    Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                }else {
                    progressDialog=new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Submitting...");
                    progressDialog.show();
                    JSONObject requestObject=new JSONObject();
                    try {
                        requestObject.put("email", etEmail.getText().toString() + "");
                        requestObject.put("password", etPassword.getText().toString() + "");

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    new PostServiceCall(AppConstants.LOGIN, requestObject) {

                        @Override
                        public void response(String response) {

                            progressDialog.dismiss();
                            User user = new GsonBuilder().create().fromJson(response, User.class);

                            if(user.getResponseId().equalsIgnoreCase("1")){
                                PrefUtils.setUser(user,LoginActivity.this);
                                Toast.makeText(LoginActivity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }


                        @Override
                        public void error(String error) {

                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Error while submitting data, try again later!",Toast.LENGTH_LONG).show();
                        }

                    }.call();


                }
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
