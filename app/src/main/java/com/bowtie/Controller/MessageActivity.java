package com.bowtie.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.bowtie.R;

public class MessageActivity extends AppCompatActivity {
    private FrameLayout btnBack, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        btnBack = (FrameLayout) findViewById(R.id.btnBack);
        btnSend = (FrameLayout) findViewById(R.id.btnSend);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MessageActivity.this, FindContactController.class));
        finish();
    }
}
