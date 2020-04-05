package com.example.shopapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopapplication.R;
import com.example.shopapplication.TemporaryDatabase;

public class ThankYou extends AppCompatActivity {
    Button OK;
    TemporaryDatabase tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        OK = findViewById(R.id.order_create_success_ok);

        tb = new TemporaryDatabase(this);

        tb.cleardata();

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThankYou.this,mainfront.class);
                startActivity(i);
            }
        });

    }
}
