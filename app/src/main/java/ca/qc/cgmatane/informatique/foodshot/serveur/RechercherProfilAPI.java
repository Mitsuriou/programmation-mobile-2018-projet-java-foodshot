package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RechercherProfilAPI extends AsyncTask<String, String, String> {
    private String pseudonymeRecherche;

    public RechercherProfilAPI(String pseudonymeRecherche) {
        this.pseudonymeRecherche = pseudonymeRecherche;
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
                .url("http://54.37.152.134/api/utilisateur/rechercher.php?pseudonyme=" + this.pseudonymeRecherche)
                .build();

        try {
            reponse = client.newCall(request).execute();
            String jsonDonneesString = reponse.body().string();
            JSONObject jsonDonneesObjet = new JSONObject(jsonDonneesString);
            Log.d("json_reponse_serveur", jsonDonneesObjet.toString());

            // statut
            String statut = jsonDonneesObjet.getString("statut");
            Log.d("json_recherche_statut", statut);

            // donnee
            String donneeString = jsonDonneesObjet.getString("donnee");

            JSONObject jsonObjectDonnee = new JSONObject(donneeString);

            String utilsateurString = jsonObjectDonnee.getString("utilisateur");
            JSONArray utilisateurJsonArray = new JSONArray(utilsateurString);

            List<JSONObject> listeDesUtilisateurs = new ArrayList<>();
            for (int i = 0; i < utilisateurJsonArray.length(); i++) {
                listeDesUtilisateurs.add(utilisateurJsonArray.getJSONObject(i));
            }

            for (JSONObject valeur : listeDesUtilisateurs) {
                Log.d("utilisateur_id", valeur.getString("id_utilisateur"));
                Log.d("utilisateur_nom", valeur.getString("nom"));
                Log.d("utilisateur_pseudonyme", valeur.getString("pseudonyme"));
            }

            // message
            String messageString = jsonDonneesObjet.getString("message");
            JSONArray messageJsonArray = new JSONArray(messageString);

            List<JSONObject> listeDesMessages = new ArrayList<>();
            for (int i = 0; i < messageJsonArray.length(); i++) {
                listeDesMessages.add(messageJsonArray.getJSONObject(i));
            }

            for (JSONObject valeur: listeDesMessages) {
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
}