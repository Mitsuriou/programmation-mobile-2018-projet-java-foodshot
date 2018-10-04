package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CallAPI extends AsyncTask<String, String, String> {

    String emailString;
    String commentString;

    public CallAPI(String email, String commnt){
        emailString = email;
        commentString = commnt;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", emailString) // A sample POST field
                .add("comment", commentString) // Another sample POST field
                .build();
        Request request = new Request.Builder()
                .url("https://yourdomain.org/callback.php") // The URL to send the data to
                .post(formBody)
                .build();
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}