package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private Context contexte;

    public GestureListener(Context contexte) {
        this.contexte = contexte;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Intent intentionNaviguerVersVueNouvellePublication = new Intent(contexte, ActiviteNouvellePublication.class);
        intentionNaviguerVersVueNouvellePublication.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contexte.startActivity(intentionNaviguerVersVueNouvellePublication);
        return false;
    }

}