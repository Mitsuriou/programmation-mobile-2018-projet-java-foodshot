package ca.qc.cgmatane.informatique.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecevoirPublicationAPI extends AsyncTask<String, String, String> {

    private int compteur;

    public RecevoirPublicationAPI(int compteur) {
        this.compteur = compteur;
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
            data.put("compteurPage", compteur);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("http://54.37.152.134/api/publication/RecevoirPublicationPage.php")
                .post(body)
                .build();

        try {
            Response reponse = client.newCall(request).execute();
            Log.d("reponse_serveur", reponse.body().string());
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
