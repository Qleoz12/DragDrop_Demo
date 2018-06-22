package com.dragdrop_demo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by k.key on 22/04/2018.
 */

public class DataHolder
{
    private String data;
    private int score=0;
    private int scoreErrore=0;

    public DataHolder() {

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScoreErrore() {
        return scoreErrore;
    }

    public void setScoreErrore(int scoreErrore) {
        this.scoreErrore = scoreErrore;
    }

    //dont touch
    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
    //usage
    //String data = DataHolder.getInstance().getData();


    public DataHolder(int score, int scoreErrore) {
        this.score = score;
        this.scoreErrore = scoreErrore;
    }

}
