package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleUtilisateur;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthentificationAPI extends AsyncTask<String, String, String> {
    private String pseudonyme;
    private String mdpHash;

    private boolean statut;
    private ModeleUtilisateur utilisateurCourant;
    private List<ModeleMessage> listeMessages;

    public AuthentificationAPI(String pseudonyme, String mdpHash) {
        this.pseudonyme = pseudonyme;
        this.mdpHash = mdpHash;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        Response reponse;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        JSONObject data = new JSONObject();
        try {
            data.put("pseudonyme", this.pseudonyme)
                    .put("mdp_hash", this.mdpHash);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("http://54.37.152.134/api/utilisateur/auth.php")
                .post(body)
                .build();

        try {
            reponse = client.newCall(request).execute();

            if (!reponse.isSuccessful())
                throw new IOException("Unexpected code " + reponse.toString());

            String jsonDonneesString = reponse.body().string();
            JSONObject jsonDonneesObjet = new JSONObject(jsonDonneesString);
            Log.d("json_reponse_serveur", jsonDonneesObjet.toString());

            // statut
            String statutString = jsonDonneesObjet.getString("statut");
            this.statut = statutString.equals("true");

            // donnee
            String donneeString = jsonDonneesObjet.getString("donnee");
            JSONObject jsonObjectDonnee = new JSONObject(donneeString);

            String utilsateurString = jsonObjectDonnee.getString("utilisateur");
            JSONArray utilisateurJsonArray = new JSONArray(utilsateurString);

            if (utilisateurJsonArray.length() == 0) {
                this.utilisateurCourant = null;
            }
            else {
                JSONObject utilisateurJson = new JSONObject(utilisateurJsonArray.getJSONObject(0).toString());
                this.utilisateurCourant = new ModeleUtilisateur(
                        utilisateurJson.getInt("id_utilisateur"),
                        utilisateurJson.getString("nom"),
                        utilisateurJson.getString("pseudonyme"),
                        utilisateurJson.getString("url_image"),
                        utilisateurJson.getInt("nombre_mention_aime"),
                        utilisateurJson.getString("creation")
                );
            }

            // message
            String messageString = jsonDonneesObjet.getString("message");
            JSONArray messageJsonArray = new JSONArray(messageString);

            List<JSONObject> listeDesMessages = new ArrayList<>();
            for (int i = 0; i < messageJsonArray.length(); i++) {
                listeDesMessages.add(messageJsonArray.getJSONObject(i));
            }

            this.listeMessages = new ArrayList<>();
            for (JSONObject valeur: listeDesMessages) {
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

    public ModeleUtilisateur getUtilisateurCourant() {
        return utilisateurCourant;
    }

    public List<ModeleMessage> getListeMessages() {
        return listeMessages;
    }
}