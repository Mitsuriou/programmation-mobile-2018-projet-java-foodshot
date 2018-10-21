package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModifierUtilisateurAPI extends AsyncTask<String, String, String> {

    private int idUtilisateur;
    private String chaineNom;
    private String chaineMdpHash;

    public ModifierUtilisateurAPI(int idUtilisateur, String nom, String mdpHash) {
        this.idUtilisateur = idUtilisateur;
        this.chaineNom = nom;
        this.chaineMdpHash = mdpHash;
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
                    .put("nom", this.chaineNom)
                    .put("mdp_hash", this.chaineMdpHash);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody corps = RequestBody.create(JSON, data.toString());
        Request request = new Request.Builder()
                .url("http://54.37.152.134/api/utilisateur/modifier.php")
                .post(corps)
                .build();

        try {
            reponse = client.newCall(request).execute();

            if (!reponse.isSuccessful())
                throw new IOException("Code non attendu : " + reponse.toString());

            return reponse.body().string();
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