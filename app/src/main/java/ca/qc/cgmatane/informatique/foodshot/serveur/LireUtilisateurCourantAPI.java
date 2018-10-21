package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LireUtilisateurCourantAPI extends AsyncTask<String, String, String> {

    private int idUtilisateur;
    private boolean statut;
    private String nomUtilisateurCourant;
    private String urlImageUtilisateurCourant;
    private int nbrMentionAimeUtilisateurCourant;

    public LireUtilisateurCourantAPI(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        Response reponse;
        OkHttpClient client = new OkHttpClient();

        Request requete = new Request.Builder()
                .url("http://54.37.152.134/api/utilisateur/lire_un.php?id_utilisateur=" + this.idUtilisateur)
                .build();

        try {
            reponse = client.newCall(requete).execute();

            if (!reponse.isSuccessful())
                throw new IOException("Code non attendu : " + reponse.toString());

            String jsonDonneesString = reponse.body().string();
            JSONObject jsonDonneesObjet = new JSONObject(jsonDonneesString);

            // statut
            String statutString = jsonDonneesObjet.getString("statut");
            this.statut = statutString.equals("true");

            // donnee
            String donneeString = jsonDonneesObjet.getString("donnee");
            JSONObject jsonObjectDonnee = new JSONObject(donneeString);

            String utilisateurString = jsonObjectDonnee.getString("utilisateur");
            JSONArray utilisateurJsonArray = new JSONArray(utilisateurString);

            JSONObject utilisateurJson = new JSONObject(utilisateurJsonArray.getJSONObject(0).toString());
            this.nomUtilisateurCourant = utilisateurJson.getString("nom");
            this.urlImageUtilisateurCourant = utilisateurJson.getString("url_image");
            this.nbrMentionAimeUtilisateurCourant = utilisateurJson.getInt("nombre_mention_aime");

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

    public String getNomUtilisateurCourant() {
        return nomUtilisateurCourant;
    }

    public String getUrlImageUtilisateurCourant() {
        return urlImageUtilisateurCourant;
    }

    public int getNbrMentionAimeUtilisateurCourant() {
        return nbrMentionAimeUtilisateurCourant;
    }
}