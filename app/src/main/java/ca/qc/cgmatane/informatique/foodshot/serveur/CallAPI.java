package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        JSONObject data = new JSONObject();
        try {
            data.put("nom", chaineNom)
                    .put("pseudonyme", chainePseudonyme)
                    .put("mdp_hash", chaineMdpHash);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jason", "" + data);

        RequestBody body = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("http://54.37.152.134/api/utilisateur/creer.php")
                .post(body)
                .build();

        try {
            Response reponse = client.newCall(request).execute();
            if (!reponse.isSuccessful())
                throw new IOException("Unexpected code " + reponse.toString());
            return reponse.body().string();
        } catch (Exception e) {
            Log.d("reponse", "exception");
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}