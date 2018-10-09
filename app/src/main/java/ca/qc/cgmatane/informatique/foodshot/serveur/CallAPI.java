package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CallAPI extends AsyncTask<String, String, String> {
    private String chaineNom;
    private String chainePseudonyme;
    private String chaineMdpHash;

    public CallAPI(String nom, String pseudonyme, String mdpHash) {
        chaineNom = nom;
        chainePseudonyme = pseudonyme;
        chaineMdpHash = mdpHash;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("nom", chaineNom)
                .add("pesudonyme", chainePseudonyme)
                .add("mdp_hash", chaineMdpHash)
                .build();
        Request request = new Request.Builder()
                .url("http://54.37.152.134/api/utilisateur/creer.php")
                .post(formBody)
                .build();
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}