package ca.qc.cgmatane.informatique.foodshot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActiviteNouveauPoste extends AppCompatActivity {
    private final int CAM_REQUEST = 1313;

    protected ImageView conteneurImage;
    protected Button boutonCaptureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_nouveau_poste);

        this.conteneurImage = (ImageView) findViewById(R.id.conteneur);

        this.boutonCaptureImage = (Button)findViewById(R.id.bouton_photo);
        this.boutonCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionCapturePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentionCapturePhoto, CAM_REQUEST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (CAM_REQUEST == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            conteneurImage.setImageBitmap(bitmap);
        }
    }
}
