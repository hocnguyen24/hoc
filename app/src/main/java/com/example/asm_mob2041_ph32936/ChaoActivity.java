package com.example.asm_mob2041_ph32936;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChaoActivity.this, DangNhapActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_top,R.anim.slide_out_down);
                finish();
            }
        },3000);
    }
}