package com.dragdrop_demo.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dragdrop_demo.R;

import java.util.ArrayList;

public class GeneratorGame {

    ArrayList<LinearLayout> columns= new ArrayList<> ();
    Activity activity;

    public ArrayList<LinearLayout> GenerateColumns(int numberColumns, int numberRows)
    {
        for (int x = 0; x < numberColumns; x++)
        {
            LinearLayout parent = new LinearLayout(activity);
            LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
            lp.gravity = Gravity.CENTER;
            parent.setLayoutParams(lp);
            parent.setPadding(5,5,5,5);
            parent.setOrientation(LinearLayout.VERTICAL);

            for (int y = 0; y < numberRows; y++)
            {
                ImageView iv = new ImageView(activity);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT,1));
                iv.setLayoutParams(lp);
                //iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if ( Math.round( Math.random())>0)
                {
                    iv.setImageResource(R.drawable.dill_and_scut);
                    iv.setTag("Animal");
                }
                else
                {
                    iv.setImageResource(R.mipmap.ic_launcher);
                    iv.setTag("Number");
                }
                parent.addView(iv);

            }
            columns.add(parent);
        }
        return columns;
    }

    public GeneratorGame(Activity activity) {
        this.activity = activity;
    }
}
