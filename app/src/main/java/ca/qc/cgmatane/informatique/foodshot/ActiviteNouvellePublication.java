package ca.qc.cgmatane.informatique.foodshot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActiviteNouvellePublication extends AppCompatActivity {
    private final int DEMANDE_CAM = 1313;

    protected ImageView conteneurImage;
    protected Button boutonCaptureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_nouvelle_publication);

        this.conteneurImage = (ImageView) findViewById(R.id.conteneur_photo_capturee);

        this.boutonCaptureImage = (Button)findViewById(R.id.bouton_demarrer_appareil_photo);
        this.boutonCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionCapturePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentionCapturePhoto, DEMANDE_CAM);
            }
        });

    }

    @Override
    public void onActivityResult(int codeDeRequete, int codeDeResultat, Intent donnees) {
        super.onActivityResult(codeDeRequete, codeDeResultat, donnees);

        if (DEMANDE_CAM == codeDeRequete && codeDeResultat == RESULT_OK) {
            Bitmap bitmap = (Bitmap) donnees.getExtras().get("data");
            conteneurImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
