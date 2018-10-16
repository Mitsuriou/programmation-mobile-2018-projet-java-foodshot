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

    private int compteur, dernierId=-1, id_utilisateur;
    private boolean statut, progress=false;
    private List<Publication> listePublication;
    private List<ModeleMessage> listeMessages;

    public RecevoirPublicationAPI(int id_utilisateur ,int compteur, int dernierId) {
        this.compteur = compteur;
        this.dernierId = dernierId;
        this.id_utilisateur = id_utilisateur;
    }

    public RecevoirPublicationAPI(int id_utilisateur, int compteur) {
        this.compteur = compteur;
        this.id_utilisateur = id_utilisateur;
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
                .url("http://54.37.152.134/api/publication/lire_pagination.php?id_utilisaeur=" + id_utilisateur + "&page=" + compteur + "&dernierId=" + dernierId)
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

                this.listePublication.add(new Publication(
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

                Log.d("utilisateur_id", "" + valeur.getInt("id_publication"));
                Log.d("utilisateur_nom", valeur.getString("titre"));
                Log.d("utilisateur_pseudonyme", valeur.getString("description"));
                Log.d("utilisateur_pseudonyme", valeur.getString("url_image"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getDouble("latitude"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getDouble("longitude"));
                Log.d("utilisateur_j_aime","" + valeur.getBoolean("j_aime"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getInt("nombre_mention_aime"));
                Log.d("utilisateur_pseudonyme", valeur.getString("pseudonyme_utilisateur"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getInt("id_utilisateur"));
                Log.d("utilisateur_pseudonyme", "" + valeur.getString("creation"));

            }
            this.progress = true;

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

    public boolean getProgress(){ return this.progress;}
}
