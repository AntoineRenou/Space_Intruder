package com.antoinerenou.findintruder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }

    public void displayHomeActivity(View view){
        finish();
    }
    @Override
    public void onBackPressed() {
        //DO NOTHING
    }
}
