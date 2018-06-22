package com.dragdrop_demo.Controller;

import android.app.Activity;
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
            parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.setOrientation(LinearLayout.VERTICAL);
            for (int y = 0; y < numberRows; y++)
            {
                ImageView iv = new ImageView(activity);
                iv.setMaxWidth(50);
                iv.setMaxHeight(50);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if ( Math.round( Math.random())>0)
                {
                    iv.setImageResource(R.mipmap.ic_launcher);
                    iv.setTag("Animal");
                }
                else
                {
                    iv.setImageResource(R.drawable.dill_and_scut);
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
