package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SupprimerAimeAPI extends AsyncTask<String, String, String> {

    private int idUtilisateur;
    private int idPublication;
    private List<ModeleMessage> listeMessages;

    public SupprimerAimeAPI(int idUtilisateur, int idPublication) {
        this.idUtilisateur = idUtilisateur;
        this.idPublication = idPublication;
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
            data.put("id_utilisateur", this.idUtilisateur)
                    .put("id_publication", this.idPublication);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody corps = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("http://54.37.152.134/api/aime/supprimer.php")
                .post(corps)
                .build();

        try {
            reponse = client.newCall(request).execute();

            if (!reponse.isSuccessful())
                throw new IOException("Unexpected code " + reponse.toString());

            String jsonDonneesString = reponse.body().string();
            JSONObject jsonDonneesObjet = new JSONObject(jsonDonneesString);

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

    public List<ModeleMessage> getListeMessages() {
        return listeMessages;
    }
}

