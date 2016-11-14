package com.antoinerenou.findintruder;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class SettingsActivity extends AppCompatActivity {

    public NumberPicker n;
    public int c = -1;
    byte[] icon = null;
    public SeekBar s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.spaceinvader1);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        icon = stream.toByteArray();

        getComponentResource();
        setNumberPicker();
        setSeekBar();
    }

    @Override
    public void onBackPressed(){
        //DO NOTHING
    }

    protected void getComponentResource(){
        n = (NumberPicker) findViewById(R.id.numberPicker);
        s = (SeekBar) findViewById(R.id.seekBar);
    }

    public void getColorResource(View view){
        TextView t = (TextView) findViewById(view.getId());
        Drawable cd = t.getBackground();
        c = ((ColorDrawable) cd).getColor();

        PorterDuffColorFilter filter = new PorterDuffColorFilter(c, PorterDuff.Mode.MULTIPLY);

        TextView t1 = (TextView) findViewById(R.id.icon1);
        TextView t2 = (TextView) findViewById(R.id.icon2);
        TextView t3 = (TextView) findViewById(R.id.icon3);
        TextView t4 = (TextView) findViewById(R.id.icon4);

        t1.getBackground().setColorFilter(filter);
        t2.getBackground().setColorFilter(filter);
        t3.getBackground().setColorFilter(filter);
        t4.getBackground().setColorFilter(filter);



    }

    public void getIconResource(View view){
        TextView t = (TextView) findViewById(view.getId());
        Drawable cd = t.getBackground();

        Bitmap bitmap = ((BitmapDrawable) cd).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        icon = baos.toByteArray();

    }

    protected void setNumberPicker(){
        n.setMinValue(2);
        n.setMaxValue(10);
    }

    protected void setSeekBar(){
        s.setMax(20);
    }

    public void displayHomeActivity(View view){
        Intent i = new Intent(this, MainActivity.class);
        if(view!=null){
            i.putExtra("entities", n.getValue());
            i.putExtra("icon", icon);
            i.putExtra("color", c);
            i.putExtra("speed", s.getProgress()+1);
        }
        else{
            i.putExtra("entities", 2);
            i.putExtra("icon", "");
            i.putExtra("color", -1);
            i.putExtra("speed", 1);
        }
        setResult(1000, i);
        finish();
    }


}
