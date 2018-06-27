package com.dragdrop_demo.Controller;

import android.app.Activity;
import android.media.MediaPlayer;

import com.dragdrop_demo.DataHolder;
import com.dragdrop_demo.R;

public class SoundManager {
    Activity activity;
    MediaPlayer mp;

    //dont touch
    private static final SoundManager holder = new SoundManager();
    public static SoundManager getInstance() {return holder;}
    //usage
    //String data = SoundManager.getInstance().getData();


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    //proces to pley success sound
    public void play_success() {
        //sounds
        Stop();
        mp = MediaPlayer.create(activity, R.raw.success);
        mp.start();
    }
    //proces to pley error sound
    public void play_error()
    {
        //sounds
        Stop();
        mp = MediaPlayer.create(activity, R.raw.error);
        mp.start();
    }

    //proces to stop error sound
    public void Stop()
    {
        //sounds
        if (mp!=null)
        {
            mp.stop();
            mp.release();
        }
    }



}
