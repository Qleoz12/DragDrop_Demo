package com.dragdrop_demo;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;

    private static final String IMAGE_VIEW_TAG = "LAUNCHER LOGO";
    private static final String IMAGE_VIEW_TAG2 = "animal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        implementEvents();
    }

    //Find all views and set Tag to all draggable views
    private void findViews() {

        imageView1 = (ImageView) findViewById(R.id.animal1);
        imageView1.setTag(IMAGE_VIEW_TAG);

        imageView2 = (ImageView) findViewById(R.id.animal2);
        imageView2.setTag(IMAGE_VIEW_TAG2);

        imageView3 = (ImageView) findViewById(R.id.animal3);
        imageView3.setTag(IMAGE_VIEW_TAG);

        imageView4 = (ImageView) findViewById(R.id.animal4);
        imageView4.setTag(IMAGE_VIEW_TAG2);

        imageView5 = (ImageView) findViewById(R.id.animal5);
        imageView5.setTag(IMAGE_VIEW_TAG);

        imageView6 = (ImageView) findViewById(R.id.animal6);
        imageView6.setTag(IMAGE_VIEW_TAG2);

    }


    //Implement long click and drag listener
    private void implementEvents() {
        //add or remove any view that you don't want to be dragged




        //add or remove any layout view that you don't want to accept dragged view
        findViewById(R.id.top_layout).setOnDragListener(this);
        findViewById(R.id.r1).setOnDragListener(this);
        findViewById(R.id.r2).setOnDragListener(this);
        findViewById(R.id.r3).setOnDragListener(this);
        findViewById(R.id.r4).setOnDragListener(this);


        imageView1.setOnTouchListener(handleTouch);
        imageView2.setOnTouchListener(handleTouch);
        imageView3.setOnTouchListener(handleTouch);
        imageView4.setOnTouchListener(handleTouch);
        imageView5.setOnTouchListener(handleTouch);
        imageView6.setOnTouchListener(handleTouch);


    }

    @Override
    public boolean onLongClick(View view)
    {
        // Displays a message containing the dragged data.
        Toast.makeText(this, "fire longclic" , Toast.LENGTH_SHORT).show();
        // Create a new ClipData.
        // This is done in two steps to provide clarity. The convenience method
        // ClipData.newPlainText() can create a plain text ClipData in one step.

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(data//data to be dragged
                    , shadowBuilder //drag shadow
                    , view//local data about the drag and drop operation
                    , 0//no needed flags
            );
        } else {
            view.startDrag(data//data to be dragged
                    , shadowBuilder //drag shadow
                    , view//local data about the drag and drop operation
                    , 0//no needed flags
            );
        }


        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);
        return true;
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

                }
                else
                {
                    View v = (View) event.getLocalState();
                    v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
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
}
