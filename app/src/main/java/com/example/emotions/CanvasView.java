package com.example.emotions;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;

import com.example.emotions.classes.Emotion;

public class CanvasView extends View implements View.OnTouchListener {

    private static final String[] EMOTIONS_LIST = {
            "Miedo",
            "sumisión",
            "Confianza",
            "Amor",
            "Alegría",
            "Optimismo",
            "Anticipación",
            "Agresividad",

            "Desprecio",
            "Asco",
            "Remordimiento",
            "Tristeza",
            "Decepción",
            "Sorpresa",
            "Susto",
            "Ira"

    };
    private static  final String HOW_YOU_FEEL = "How \r do\r you\r feel?";
    private static final int PADDING_TOP = 80;
    private static final int PADDING_RIGTH = 50;
    private static final int DOUBLE_PADDING = 120;


    private float w, h, radius, pointX, pointY, circleX, circleY;
    private int red = 255, blue = 255, green = 255;

    private Double constant;
    private  double angle;
    private boolean drawCircle;
    private boolean isSplash = true;
    private int indexWord = 0;
    private MainActivity main;

    public CanvasView (Context context,float width, float height) {

        super(context);
        main = (MainActivity) context;
        w = width;
        h = height;
        Log.d("WIDTH",w+"/"+h);

        radius = (w - 100) / 2 ;
        pointX = radius + PADDING_RIGTH;
        pointY = radius + (DOUBLE_PADDING * 2);
        this.setOnTouchListener(this);
        drawCircle = false;
        constant = ( Math.PI * 2 ) / EMOTIONS_LIST.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //int w =20,h = 20;
        if(isSplash){
            createBanner(canvas);
            isSplash = false;
        }else{
            drawCircle(canvas);
        }

    }
    private void createBanner(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(96);
        String[] textToWrite = HOW_YOU_FEEL.split("\r");
        int index = 1;
        for(String str: textToWrite) {
            canvas.drawText(str, PADDING_RIGTH, PADDING_TOP * index, paint);
            index ++;
        }
    }
    /*
    *Draw the principal circle
    *
     */
    private void drawCircle(Canvas canvas){
        // canvas.
        w = canvas.getWidth();
        h = canvas.getHeight();

        canvas.drawRGB( red,green,blue);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((3));
        paint.setColor(Color.rgb(255,255,255));

        canvas.drawCircle(pointX, pointY , radius, paint);

        //canvas.drawCircle(circleX, circleY , radius / 10, paint);
        //   paint.setColor(Color.rgb(251,255,255));
        if (drawCircle){
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(circleX,circleY , radius / 10, paint);
        }
        paint.setTextSize(60);
        float wordWidth = paint.measureText(EMOTIONS_LIST[indexWord]);
        float paddingWord = (w - wordWidth) / 2;
        canvas.drawText(EMOTIONS_LIST[indexWord], paddingWord,  DOUBLE_PADDING, paint);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!main.isButtonsVisible())
            main.setButtonHolderVisibility(true);

        String DEBUG_TAG = "TAG";
        final int action = event.getActionMasked();
        float posX = event.getX();
        float posY = event.getY();
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
              //  Log.d(DEBUG_TAG,"Action was DOWN");
                calculateAngleByPosition(posX,posY);
                calculateCirclePosition();
                calculateColor();
                toggleCircle();
                invalidate();
                return true;
            case (MotionEvent.ACTION_MOVE) :
               // Log.d(DEBUG_TAG,"Action was MOVE");
                calculateAngleByPosition(posX,posY);
                calculateCirclePosition();
                calculateColor();
                calculateEmotion();
                invalidate();
                return true;
            case (MotionEvent.ACTION_UP) :
                toggleCircle();
                invalidate();
                main.addEmotion(new Emotion(EMOTIONS_LIST[indexWord],
                        Color.rgb(red,green,blue)));
                return true;
            case (MotionEvent.ACTION_CANCEL) :
              //  Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
              //  Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
              //          "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }

    }
    /*
    * Calcula el angulo por la posición del touch
     */
    public void calculateAngleByPosition(float x, float y) {
        float oposite = pointY - y;
        float adyasente = x - pointX;
        this.angle = Math.atan2(oposite,adyasente);
      //  Log.d("RESULT",Math.toDegrees(angle)+"");
    }
    /*
    * Calcula la
     */
    private void calculateCirclePosition(){
        circleX = (float) Math.sin(angle + 1.575 ) * radius + pointX;
        circleY =  (float)Math.cos(angle + 1.575 ) * radius + pointY;
    }
    /*
    *Calculate the color by the touch position
    *
     */
    public void calculateColor(){
     //   Log.d("TAG ANGLE", "angle: "+angle);
        if (angle > 0) {
            //si el ángulo fuera en grados, lo estáriamos dividiendo por 90.
            if (angle <= 1.575){
                red=(int) Math.floor( angle * (1 / 1.575) * 255 );
                green = 255;
                blue = 0;
            }else if (angle > 1.575) {
                red = 255;
                green = 255 -  (int) Math.floor( (angle * (1 / 1.575) ) * 255 );
                blue = 0;
            }
        }else {
            if (angle >= -1.575 ) {
                red = 0;
                green =  255 +  (int) Math.floor( (angle * (1 / 1.575) ) * 255 );
                blue = (int) Math.floor( angle * (1 / 1.575) * 255 ) * -1;

            }else if (angle < -1.575) {
                red =  Math.abs ((int) Math.floor( (angle * (1 / 1.575) ) * 255 ) + 255) ;
                blue  = ((int) Math.floor( angle * (1 / 1.575) * 255  )  + 255) + 255;
                green = 0;
          //      Log.d("EMOTION_BLUE",blue+"--"+red);
            }
        }
    }
    private int getTintPorcentual (){
        return (int) Math.floor( angle * (1 / 1.575) * 255 );
    }
    private void toggleCircle(){
        drawCircle = (drawCircle) ? false : true;
    }
    void calculateEmotion(){
        int tempIndex = 0;
                if(angle < 0){
                    tempIndex = (int) Math.floor( (Math.PI + (angle * -1)) / constant );
                } else {
                    tempIndex = (int) Math.floor(angle / constant);
                }
         indexWord = tempIndex;
    }
}
