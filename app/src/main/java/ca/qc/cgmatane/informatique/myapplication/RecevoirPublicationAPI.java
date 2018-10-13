package ca.qc.cgmatane.informatique.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecevoirPublicationAPI extends AsyncTask<String, String, String> {

    private int compteur;
    private boolean statut;
    private List<Publication> listePublication;
    private List<ModeleMessage> listeMessages;

    public RecevoirPublicationAPI(int compteur) {
        this.compteur = compteur;
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
                .url("http://54.37.152.134/api/publication/lire_pagination.php?page=" + compteur)
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
                this.listePublication.add(new Publication(
                        valeur.getInt("id_publication"),
                        valeur.getString("titre"),
                        valeur.getString("description"),
                        valeur.getString("url_image"),
                        valeur.getDouble("latitude"),
                        valeur.getDouble("longitude"),
                        valeur.getInt("nombre_mention_aime"),
                        valeur.getInt("id_utilisateur"),
                        valeur.getString("pseudonyme_utilisateur"),
                        valeur.getString("url_image_utilisateur"),
                        valeur.getString("creation")
                ));

                Log.d("utilisateur_id", "" + valeur.getInt("id_publication"));
                Log.d("utilisateur_nom", valeur.getString("titre"));
                Log.d("utilisateur_pseudonyme", valeur.getString("description"));
                Log.d("utilisateur_pseudonyme", valeur.getString("url_image"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getDouble("latitude"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getDouble("longitude"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getInt("nombre_mention_aime"));
                Log.d("utilisateur_pseudonyme", valeur.getString("pseudonyme_utilisateur"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getInt("id_utilisateur"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getString("creation"));

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

    public List<Publication> getListePublication() {
        return listePublication;
    }

    public List<ModeleMessage> getListeMessages() {
        return listeMessages;
    }
}
