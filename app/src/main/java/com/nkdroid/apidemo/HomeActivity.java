package com.nkdroid.apidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nkdroid.apidemo.custom.PrefUtils;

public class HomeActivity extends AppCompatActivity {

    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogout= (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.clearCurrentUser(HomeActivity.this);
                finish();
            }
        });
    }
}
