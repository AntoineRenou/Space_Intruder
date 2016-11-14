package com.antoinerenou.findintruder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class WinActivity extends Activity {
    public long score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        setExtrasSettings();
        setScore();

        getWindow().setBackgroundDrawableResource(R.color.translucent_black);

    }

    protected void setExtrasSettings(){
        Intent i = getIntent();
        score = i.getExtras().getLong("score");
    }

    protected void setScore(){
        TextView tv = (TextView) findViewById(R.id.scoreText);
        tv.setText(score +" ms");
    }


    public void displayHomeActivity(View view){

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
    }



    public void displayPlayActivity(View view){
        finish();
    }
    @Override
    public void onBackPressed() {
        //DO NOTHING
    }
}
