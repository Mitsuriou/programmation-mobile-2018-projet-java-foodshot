package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

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
            String jsonData = reponse.body().string();
            JSONObject jObject = new JSONObject(jsonData);
            Log.d("json_reponse_serveur", jObject.toString());

            // statut
            String statut = jObject.getString("statut");
            Log.d("json_recherche_statut", statut);

            String donneeString = jObject.getString("donnee");
            JSONObject donneeJson = new JSONObject(donneeString);

            String utilisateurString = donneeJson.getString("utilisateur");
            JSONArray utilisateurJson = new JSONArray(utilisateurString);

            for (int i = 0; i < utilisateurJson.length(); i++) {
                Log.d("json_recherche_utilisateur", utilisateurJson.get(i).toString());
            }

            //message
            String messageString = jObject.getString("message");
            JSONArray messageJsonArray = new JSONArray(messageString);
            JSONObject messageJsonObject;
            for (int i = 0; i < messageJsonArray.length(); i++) {
                messageJsonObject = messageJsonArray.getJSONObject(i);
                Log.d("ALED", messageJsonObject.getString("message"));
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