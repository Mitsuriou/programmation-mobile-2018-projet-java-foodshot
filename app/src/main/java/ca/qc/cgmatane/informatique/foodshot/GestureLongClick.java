package ca.qc.cgmatane.informatique.foodshot;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureLongClick extends GestureDetector.SimpleOnGestureListener {

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i("LongTap", "Long tap");
    }

}