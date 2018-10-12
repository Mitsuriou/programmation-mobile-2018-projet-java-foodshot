package ca.qc.cgmatane.informatique.foodshot;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ActiviteNouvellePublication extends AppCompatActivity {

    private static final int DEMANDE_CAM = 1102;
    private static final String DOSSIER_PHOTO = "FoodShot";

    //localisation
    private FusedLocationProviderClient client;
    private double latitude;
    private double longitude;

    String outputFilePath;

    protected Button boutonCaptureImage;
    protected Button boutonPosterPublication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_nouvelle_publication);

        this.demanderPermissions();

        this.client = LocationServices.getFusedLocationProviderClient(this);

        this.boutonCaptureImage = (Button) findViewById(R.id.bouton_demarrer_appareil_photo);
        this.boutonCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCamera();
            }
        });

        this.boutonPosterPublication = (Button) findViewById(R.id.poster_nouvelle_publication);
        this.boutonPosterPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO call API
                trouverLocalisation();

                if (ActivityCompat.checkSelfPermission(ActiviteNouvellePublication.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    client.getLastLocation().addOnSuccessListener(ActiviteNouvellePublication.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                            else {
                                latitude = 0;
                                longitude = 0;
                            }
                        }
                    });
                }
                else {
                    latitude = 0;
                    longitude = 0;
                }

                Log.d("coord_lat", "" + latitude);
                Log.d("coord_long", "" + longitude);

                Toast.makeText(ActiviteNouvellePublication.this, "Publication postée avec succès ! :D", Toast.LENGTH_SHORT).show();
                finirActivite();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == DEMANDE_CAM)
            onCaptureImageResult();
    }

    private void finirActivite() {
        this.finish();
    }

    private void onCamera() {
        Intent intentionCapturerPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentionCapturerPhoto.resolveActivity(getPackageManager()) != null) {
            File fichierPhoto = creerFichierImage();
            Log.d("name", getPackageName());
            Uri uriPhoto = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", fichierPhoto);
            outputFilePath = fichierPhoto.getAbsolutePath();
            intentionCapturerPhoto.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
            startActivityForResult(intentionCapturerPhoto, DEMANDE_CAM);
        }
    }

    private File creerFichierImage() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nomFichierImage = "JPEG_" + timeStamp + "_";
        File dossierStockage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), DOSSIER_PHOTO + "/");
        if (!dossierStockage.exists())
            dossierStockage.mkdir();
        return new File(dossierStockage, nomFichierImage + ".jpg");
    }

    private void onCaptureImageResult() {
        if (outputFilePath != null) {
            File f = new File(outputFilePath);
            try {
                File publicFile = copyImageFile(f);
                Uri finalUri = Uri.fromFile(publicFile);
                galleryAddPic(finalUri);
                ((ImageView) findViewById(R.id.conteneur_photo_capturee)).setImageURI(finalUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File copyImageFile(File fileToCopy) throws IOException {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DOSSIER_PHOTO + "/");
        if (!storageDir.exists())
            storageDir.mkdir();
        File copyFile = new File(storageDir, fileToCopy.getName());
        copyFile.createNewFile();
        copy(fileToCopy, copyFile);
        return copyFile;
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    private void galleryAddPic(Uri contentUri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void trouverLocalisation() {

    }

    private void demanderPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, 1);
    }

}
