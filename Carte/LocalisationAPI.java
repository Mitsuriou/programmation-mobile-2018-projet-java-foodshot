package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.ActiviteNouvellePublication;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocalisationAPI extends AsyncTask<String, String, String> {

    private boolean statut;
    private double longitude, latitude;
    private List<ModelePublication> listePublication;
    private List<ModeleMessage> listeMessages;

    public LocalisationAPI(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        Response reponse;



        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("rechercher.php?latitude=" + this.latitude + "&longitude=" + this.longitude)
                .build();

        try {
            reponse = client.newCall(request).execute();
            String jsonDonneesString = reponse.body().string();
            JSONObject jsonDonneesObjet = new JSONObject(jsonDonneesString);

            // statut
            String statutString = jsonDonneesObjet.getString("statut");
            this.statut = statutString.equals("true");

            // donnee
            String donneeString = jsonDonneesObjet.getString("donnee");
            JSONObject jsonObjectDonnee = new JSONObject(donneeString);

            String publicationString = jsonObjectDonnee.getString("publication");
            JSONArray publicationJsonArray = new JSONArray(publicationString);

            List<JSONObject> listeDesPublicationsJson = new ArrayList<>();
            for (int i = 0; i < publicationJsonArray.length(); i++) {
                listeDesPublicationsJson.add(publicationJsonArray.getJSONObject(i));
            }

            this.listePublication = new ArrayList<>();
            for (JSONObject valeur : listeDesPublicationsJson) {

                String urlImage = valeur.getString("url_image");
                if ((urlImage != null) || (urlImage.equals(""))) {
                    urlImage = "https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg";
                }

                String urlProfil = valeur.getString("url_image_utilisateur");
                if ((urlProfil == null) || (urlProfil.equals(""))) {
                    urlProfil = "https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg";
                }

                this.listePublication.add(new ModelePublication(
                        valeur.getInt("id_publication"),
                        valeur.getString("titre"),
                        valeur.getString("description"),
                        urlImage,
                        valeur.getDouble("latitude"),
                        valeur.getDouble("longitude"),
                        valeur.getBoolean("j_aime"),
                        valeur.getInt("nombre_mention_aime"),
                        valeur.getInt("id_utilisateur"),
                        valeur.getString("pseudonyme_utilisateur"),
                        urlProfil,
                        valeur.getString("creation")
                ));
            }

            // message
            String messageString = jsonDonneesObjet.getString("message");
            JSONArray messageJsonArray = new JSONArray(messageString);

            List<JSONObject> listeDesMessages = new ArrayList<>();
            for (int i = 0; i < messageJsonArray.length(); i++) {
                listeDesMessages.add(messageJsonArray.getJSONObject(i));
            }

            this.listeMessages = new ArrayList<>();
            for (JSONObject valeur : listeDesMessages) {
                this.listeMessages.add(new ModeleMessage(
                        valeur.getInt("code"),
                        valeur.getString("type"),
                        valeur.getString("message")
                ));
                Log.d("message_code", valeur.getString("code"));
                Log.d("message_type", valeur.getString("type"));
                Log.d("message_message", valeur.getString("message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public boolean isStatut() {
        return statut;
    }

    public List<ModelePublication> getListePublication() {
        return listePublication;
    }

    public List<ModeleMessage> getListeMessages() {
        return listeMessages;
    }
}