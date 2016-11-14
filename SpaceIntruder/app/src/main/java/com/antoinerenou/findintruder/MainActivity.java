package com.antoinerenou.findintruder;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    public int entities = 2;
    public byte[] icon = null;
    public int color = -1;
    public int speed = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		/*
        final MediaPlayer mpl = new MediaPlayer();
        mpl.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mpl.create(this, R.raw.spaceinvaders1);

        mpl.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mp == mpl) {
                    mpl.start();
                }
            }
        });*/

        setContentView(R.layout.activity_main);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.spaceinvader1);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        icon = stream.toByteArray();
    }

    protected void setExtrasSettings(Intent settingsIntent){
        entities = settingsIntent.getExtras().getInt("entities");

        icon = settingsIntent.getExtras().getByteArray("icon");
        color = settingsIntent.getExtras().getInt("color");
        speed = settingsIntent.getExtras().getInt("speed");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        setExtrasSettings(data);
    }

    public void displayPlayActivity(View view){
        Intent i = new Intent(this, AnimationActivity.class);
        i.putExtra("entities", entities);
        i.putExtra("icon", icon);
        i.putExtra("color", color);
        i.putExtra("speed", speed);
        startActivity(i);
    }

    public void displaySettingsActivity(View view){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivityForResult(i,1000);
    }


    public void displayRulesActivity(View view){
        Intent i = new Intent(this, RulesActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        //DO NOTHING

    }

}
