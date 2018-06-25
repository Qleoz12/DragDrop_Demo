package com.dragdrop_demo;


import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dragdrop_demo.Controller.GeneratorGame;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnDragListener, ScoreFragment.OnFragmentInteractionListener,View.OnClickListener
{

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;

    private static final String IMAGE_VIEW_TAG = "Number";
    private static final String IMAGE_VIEW_TAG2 = "Animal";

    LinearLayout grid= null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        //implementEvents();
        DataHolder.getInstance().setScore(0);
        DataHolder.getInstance().setScoreErrore(0);
    }

    //Find all views and set Tag to all draggable views
    private void findViews() {
        int column=2;
        int row=3;


        grid = (LinearLayout) findViewById(R.id.top_layout);
        grid.removeAllViews();

        GeneratorGame gen= new GeneratorGame(this);
        ArrayList<LinearLayout> columns=gen.GenerateColumns(column,row);
        for (int x = 0; x < columns.size(); x++)
        {

            implementEvents(columns.get(x));
            grid.addView(columns.get(x));
            /*LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) columns.get(x).getLayoutParams();
            LinearLayout.LayoutParams lParamsson= (LinearLayout.LayoutParams) columns.get(x).getChildAt(0).getLayoutParams();
            System.out.println("su peso hijos es: "+ lParamsson.weight);
            ImageView im=(ImageView) columns.get(x).getChildAt(0);
            System.out.println("su crop hijos es: "+ im.getScaleType());
            System.out.println("su peso columnas es: "+lParams.weight);*/
        }

        //LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) grid.getLayoutParams();
        //System.out.println("su peso es: "+lParams.weight);

    }


    //Implement long click and drag listener
    private void implementEvents(LinearLayout linearLayout) {
        //add or remove any view that you don't want to be dragged
        //add or remove any layout view that you don't want to accept dragged view
        //Implement long click and drag listener
        for (int x = 0; x < linearLayout.getChildCount(); x++)
        {
            linearLayout.getChildAt(x).setOnTouchListener(handleTouch);
        }

        findViewById(R.id.top_layout).setOnDragListener(this);
        findViewById(R.id.r1).setOnDragListener(this);
        findViewById(R.id.r2).setOnDragListener(this);
        findViewById(R.id.r3).setOnDragListener(this);
        findViewById(R.id.r4).setOnDragListener(this);

    }





    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    @Override
    public boolean onDrag(View view, DragEvent event)
    {

        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.

                    //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                    // Invalidate the view to force a redraw in the new tint
                    //  view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();
                // Turns off any color tints
                view.getBackground().clearColorFilter();
                // Invalidates the view to force a redraw
                view.invalidate();
                LinearLayout container = (LinearLayout) view;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                String namelayoiut=container.getResources().getResourceEntryName(container.getId());
                Log.i("TAG name",namelayoiut);
                if (namelayoiut.equals("r1") && dragData.equals(IMAGE_VIEW_TAG2))
                {
                    View v = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) v.getParent();
                    owner.removeView(v);//remove the dragged view
                    container.addView(v);//Add the dragged view
                    v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                    //score
                    //if you added fragment via layout xml
                    android.app.Fragment tt=getFragmentManager().findFragmentById(R.id.fragment);
                    ScoreFragment tx=(ScoreFragment) tt;
                    DataHolder.getInstance().setScore(DataHolder.getInstance().getScore()+1);
                    tx.setScoreG(""+DataHolder.getInstance().getScore());

                }
                else if (namelayoiut.equals("r2") && dragData.equals(IMAGE_VIEW_TAG))
                {
                    View v = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) v.getParent();
                    owner.removeView(v);//remove the dragged view
                    container.addView(v);//Add the dragged view
                    v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                    //score
                    //if you added fragment via layout xml
                    android.app.Fragment tt=getFragmentManager().findFragmentById(R.id.fragment);
                    ScoreFragment tx=(ScoreFragment) tt;
                    DataHolder.getInstance().setScore(DataHolder.getInstance().getScore()+1);
                    tx.setScoreG(""+DataHolder.getInstance().getScore());
                }
                else
                {
                    View v = (View) event.getLocalState();
                    v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                    //set error
                    //score
                    //if you added fragment via layout xml
                    android.app.Fragment tt=getFragmentManager().findFragmentById(R.id.fragment);
                    ScoreFragment tx=(ScoreFragment) tt;
                    DataHolder.getInstance().setScoreErrore(DataHolder.getInstance().getScoreErrore()+1);
                    tx.setScoreE(""+DataHolder.getInstance().getScoreErrore());

                }
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // Does a getResult(), and displays what happened.
                if (event.getResult()) {
                    //Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                }

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }




    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event)
        {

            int x = (int) event.getX();
            int y = (int) event.getY();

            String viewTag= (String) view.getTag();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("TAG", "touched down");
                    Log.i("TAG debug ","::"+ viewTag);
                    // Create a new ClipData.Item from the ImageView object's tag
                    ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

                    // Create a new ClipData using the tag as a label, the plain text MIME type, and
                    // the already-created item. This will create a new ClipDescription object within the
                    // ClipData, and set its MIME type entry to "text/plain"
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                    ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

                    // Instantiates the drag shadow builder.
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                    // Starts the drag
                    view.startDrag(data//data to be dragged
                            , shadowBuilder //drag shadow
                            , view//local data about the drag and drop operation
                            , 0//no needed flags
                    );

                    //Set view visibility to INVISIBLE as we are going to drag the view
                    view.setVisibility(View.INVISIBLE);
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "touched up");
                    break;
            }

            return true;
        }
    };


    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.top_layout:

                System.out.println("su peso es: "+grid.getWeightSum());
            default:
                System.out.println("no es lo que buscas");
        }
    }
}
