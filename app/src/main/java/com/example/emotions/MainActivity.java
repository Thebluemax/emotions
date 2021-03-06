package com.example.emotions;

import android.drm.DrmStore;
import android.graphics.Point;
import android.os.Bundle;

import com.example.emotions.classes.Emotion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CanvasView canvas;
    private LinearLayout buttonHolder;
    private List<Emotion> emotionList;
    private Emotion activeEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.holder);
        buttonHolder = (LinearLayout) findViewById(R.id.button_holder);

        setButtonHolderVisibility(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int toolbarHeight = toolbar.getHeight();
        Point size = getWindowSize();
        canvas = new CanvasView(this,size.x,size.y - toolbarHeight);
        layout.addView(canvas);

        emotionList = new ArrayList<Emotion>();
        FloatingActionButton saveBtn = findViewById(R.id.save);
        FloatingActionButton cancelBtn = findViewById(R.id.close);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotionList.add(activeEmotion);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public boolean isButtonsVisible(){
        return buttonHolder.getVisibility() == View.VISIBLE;
    }

    public void setButtonHolderVisibility(boolean visibility){
        int visible = visibility ? View.VISIBLE : View.INVISIBLE;
        buttonHolder.setVisibility(visible);
    }

    public void addEmotion(Emotion emotion){
        //emotionList.add(emotion);
        activeEmotion = emotion;
    }
    private Point getWindowSize(){
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        return size;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
