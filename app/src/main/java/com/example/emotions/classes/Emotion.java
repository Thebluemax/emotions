package com.example.emotions.classes;

import android.graphics.Color;

import java.util.Date;

public class Emotion {

    private String emotionName;
    private int emotionColor;
    private Date date;
    private String info;

    public  Emotion(String name, int color){

        setEmotionName(name);
        setEmotionColor(color);
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmotionName() {
        return emotionName;
    }

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }

    public int getEmotionColor() {
        return emotionColor;
    }

    public void setEmotionColor(int emotionColor) {
        this.emotionColor = emotionColor;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
