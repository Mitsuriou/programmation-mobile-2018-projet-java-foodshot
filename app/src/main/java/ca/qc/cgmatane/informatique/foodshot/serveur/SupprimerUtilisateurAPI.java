package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SupprimerUtilisateurAPI extends AsyncTask<String, String, String> {
    private int idUtilisateur;

    public SupprimerUtilisateurAPI(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Response reponse;

        OkHttpClient client = new OkHttpClient();
        JSONObject data = new JSONObject();
        try {
            data.put("id_utilisateur", this.idUtilisateur);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("http://54.37.152.134/api/utilisateur/supprimer.php")
                .post(body)
                .build();

        try {
            reponse = client.newCall(request).execute();
            String jsonData = reponse.body().string();
            JSONObject jObject = new JSONObject(jsonData);
            //Log.d("jsonDelete", jObject.toString());
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