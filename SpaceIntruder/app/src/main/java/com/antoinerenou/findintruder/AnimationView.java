package com.antoinerenou.findintruder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Random;

/**
 * Created by AR on 11/10/2016.
 */
public class AnimationView extends View {
    int screenSizeX;
    int screenSizeY;

    Bitmap[] bm;
    Path[] animPath;
    PathMeasure[] pathMeasure;
    float[] pathLength;
    float[][] pos;
    float[] distance;
    float[] tan;
    float step;
    int color;
    int intru;

    Bitmap[] resultBitmap;
    Paint[] p;

    Matrix matrix;

    public AnimationView(Context context, Bitmap[] bm, int vitesse, int color, int intru) {
        super(context);
        screenSizeX = getResources().getDisplayMetrics().widthPixels;
        screenSizeY = getResources().getDisplayMetrics().heightPixels-150;

        this.bm = bm;
        this.color = color;
        this.intru = intru;

        pathMeasure = new PathMeasure[this.bm.length];
        pathLength = new float[this.bm.length];
        pos = new float[this.bm.length][2];
        animPath = new Path[this.bm.length];
        distance = new float[this.bm.length];
        resultBitmap = new Bitmap[this.bm.length];
        p = new Paint[this.bm.length];
        for(int j=0;j<this.bm.length;j++){
            animPath[j]= getRandomPath();
            pathMeasure[j] = new PathMeasure(animPath[j],false);
            pathLength[j] = pathMeasure[j].getLength();

            resultBitmap[j] = Bitmap.createBitmap(bm[j], 0, 0,
                    bm[j].getWidth() - 1, bm[j].getHeight() - 1);
            p[j] = new Paint();
            ColorFilter filter;
            if(j==intru){
                filter = new LightingColorFilter(color & 0xffa0a0a0, 1);
            } else {

                filter = new LightingColorFilter(color, 1);
            }
            p[j].setColorFilter(filter);
        }

        step = vitesse;

        matrix = new Matrix();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        tan = new float[2];

        for(int j=0; j<bm.length;j++){
            if(distance[j] < pathLength[j]){
                pathMeasure[j].getPosTan(distance[j], pos[j], tan);

                matrix.reset();
                matrix.postTranslate(pos[j][0]-bm[j].getWidth()/2, pos[j][1]-bm[j].getHeight()/2);

                canvas.drawBitmap(resultBitmap[j], matrix, p[j]);

                distance[j] += step;
            }else{
                distance[j] = 0;
            }
        }
        invalidate();
    }
    public Path getRandomPath(){
        Random randX = new Random();
        Random randY = new Random();

        Path p = new Path();
        p.moveTo(randX.nextInt(screenSizeX), randY.nextInt(screenSizeY));
        for(int j=0; j<10;j++) {
            p.lineTo(randX.nextInt(screenSizeX), randY.nextInt(screenSizeY));
        }
        p.close();

        return p;
    }


}
