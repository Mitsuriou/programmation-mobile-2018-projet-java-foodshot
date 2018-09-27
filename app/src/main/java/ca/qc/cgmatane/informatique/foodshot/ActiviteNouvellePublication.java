package ca.qc.cgmatane.informatique.foodshot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ActiviteNouvellePublication extends AppCompatActivity {
    private static final int DEMANDE_CAM = 111;
    private Uri uriImageAUpload;

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
                capturerImageCamera();
            }
        });
    }

    @Override
    public void onActivityResult(int codeDeRequete, int codeDeResultat, Intent donnees) {
        super.onActivityResult(codeDeRequete, codeDeResultat, donnees);

        if (codeDeRequete == DEMANDE_CAM && codeDeResultat == Activity.RESULT_OK) {
            if (uriImageAUpload != null) {
                Uri imageSelectionnee = uriImageAUpload;
                getContentResolver().notifyChange(imageSelectionnee, null);
                Bitmap bitmapReduitEnTaille = getBitmap(uriImageAUpload.getPath());
                if (bitmapReduitEnTaille != null) {
                    conteneurImage.setImageBitmap(bitmapReduitEnTaille);
                }
                else {
                    Toast.makeText(this, "Erreur lors de la capture de l'image 1", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "Erreur lors de la capture de l'image 2", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in;
        try {
            final int IMAGE_MAX_SIZE = 1200000; //1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    private void capturerImageCamera() {
        Intent intentionChoix = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        Uri uriImage = FileProvider.getUriForFile(ActiviteNouvellePublication.this, "com.foodshot.provider", f);
        intentionChoix.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        uriImageAUpload = uriImage;

        Log.d("uri", uriImage.getPath());

        startActivityForResult(intentionChoix, DEMANDE_CAM);
    }
}
