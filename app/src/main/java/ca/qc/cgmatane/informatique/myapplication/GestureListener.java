package ca.qc.cgmatane.informatique.myapplication;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    public static final String TAG = "GestureListener";

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "Double tap" );
        return false;
    }

}