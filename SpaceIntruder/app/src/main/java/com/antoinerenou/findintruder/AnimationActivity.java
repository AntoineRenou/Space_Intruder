package com.antoinerenou.findintruder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

public class AnimationActivity extends Activity implements View.OnTouchListener{

    public int entities;
    public byte[] icon;
    public int color;
    public int speed;
    private ShapeDrawable mDrawable;
    int ScreenWidth;
    int ScreenHeight;
    AnimationView animV;
    Bitmap[] bm;
    int intru;
    long startTime,stopTime;
   // public MediaPlayer mpExplo = MediaPlayer.create(this, R.raw.explosion);
//    public MediaPlayer mpShoot = MediaPlayer.create(this, R.raw.shoot);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setParameters();
    }

    @Override
    public void onResume(){
        super.onResume();
        setParameters();

    }

    public void setParameters(){
        setContentView(R.layout.activity_animation);
        setExtrasSettings();
        startTime = System.currentTimeMillis();
        Display display = getWindowManager().getDefaultDisplay();
        Point sizeProx = new Point();
        display.getSize(sizeProx);
        ScreenWidth = sizeProx.x;
        ScreenHeight = sizeProx.y;

        Random rand = new Random();
        intru = rand.nextInt(entities);

        bm = new Bitmap[entities];
        for(int j=0; j<bm.length;j++){
            bm[j] = BitmapFactory.decodeByteArray(icon, 0, icon.length);
        }


        LinearLayout Container = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_animation, null);
        animV = new AnimationView(getApplicationContext(),bm,speed,color,intru);
        Container.addView(animV);
        animV.setOnTouchListener(this);
        setContentView(Container);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(animV.pos[intru][0]-100<event.getX() && event.getX()<animV.pos[intru][0]+100 &&
                animV.pos[intru][1]-100<event.getY() && event.getY()<animV.pos[intru][1]+100) {
            //final MediaPlayer mpExplo = new MediaPlayer();
            final MediaPlayer mpExplo = getMediaPlayer(this);
            mpExplo.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mpExplo.create(this, R.raw.explosion);

            mpExplo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp == mpExplo) {
                        mpExplo.start();
                    }
                }
            });

            stopTime = System.currentTimeMillis();
            long score = stopTime-startTime;
            startTime = 0;
            stopTime = 0;
            setWinActivity(score);
        } else {
            failureEvent();
        }
        return false;
    }

    protected void setWinActivity(long score){
        Intent i = new Intent(this, WinActivity.class);
        i.putExtra("score", score);
        startActivity(i);
    }

    protected void failureEvent(){
        //mpShoot.start();
        System.out.println("_____________________FAIL__________________");

        //final MediaPlayer mpShoot = new MediaPlayer();
        final MediaPlayer mpShoot = getMediaPlayer(this);
        mpShoot.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mpShoot.create(this, R.raw.explosion);

        mpShoot.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mp == mpShoot) {
                    mpShoot.start();
                }
            }
        });
    }

    protected void setExtrasSettings(){
        Intent i = getIntent();
        entities = i.getExtras().getInt("entities");
        icon = i.getExtras().getByteArray("icon");
        color = i.getExtras().getInt("color");
        speed = i.getExtras().getInt("speed");
    }


    public void displayHomeActivity(View view){
        finish();
    }
    @Override
    public void onBackPressed() {
        //DO NOTHING
    }

    static MediaPlayer getMediaPlayer(Context context){

    MediaPlayer mediaplayer = new MediaPlayer();

    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
        return mediaplayer;
    }

    try {
        Class<?> cMediaTimeProvider = Class.forName( "android.media.MediaTimeProvider" );
        Class<?> cSubtitleController = Class.forName( "android.media.SubtitleController" );
        Class<?> iSubtitleControllerAnchor = Class.forName( "android.media.SubtitleController$Anchor" );
        Class<?> iSubtitleControllerListener = Class.forName( "android.media.SubtitleController$Listener" );

        Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

        Object subtitleInstance = constructor.newInstance(context, null, null);

        Field f = cSubtitleController.getDeclaredField("mHandler");

        f.setAccessible(true);
        try {
            f.set(subtitleInstance, new Handler());
        }
        catch (IllegalAccessException e) {return mediaplayer;}
        finally {
            f.setAccessible(false);
        }

        Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

        setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
        //Log.e("", "subtitle is setted :p");
    } catch (Exception e) {}

    return mediaplayer;
}




}
