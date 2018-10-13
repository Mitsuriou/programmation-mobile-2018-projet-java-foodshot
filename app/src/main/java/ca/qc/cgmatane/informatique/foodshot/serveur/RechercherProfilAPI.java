package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleUtilisateurRecherche;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RechercherProfilAPI extends AsyncTask<String, String, String> {
    private String pseudonymeRecherche;

    private boolean statut;
    private List<ModeleUtilisateurRecherche> listeUtilisateurs;
    private List<ModeleMessage> listeMessages;

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

            if (!reponse.isSuccessful())
                throw new IOException("Unexpected code " + reponse.toString());

            String jsonDonneesString = reponse.body().string();
            JSONObject jsonDonneesObjet = new JSONObject(jsonDonneesString);

            // statut
            String statutString = jsonDonneesObjet.getString("statut");

            this.statut = statutString.equals("true");

            // donnee
            String donneeString = jsonDonneesObjet.getString("donnee");
            JSONObject jsonObjectDonnee = new JSONObject(donneeString);

            String utilsateurString = jsonObjectDonnee.getString("utilisateur");
            JSONArray utilisateurJsonArray = new JSONArray(utilsateurString);

            List<JSONObject> listeDesUtilisateursJson = new ArrayList<>();
            for (int i = 0; i < utilisateurJsonArray.length(); i++) {
                listeDesUtilisateursJson.add(utilisateurJsonArray.getJSONObject(i));
            }

            this.listeUtilisateurs = new ArrayList<>();
            for (JSONObject valeur : listeDesUtilisateursJson) {
                this.listeUtilisateurs.add(new ModeleUtilisateurRecherche(
                        valeur.getInt("id_utilisateur"),
                        valeur.getString("nom"),
                        valeur.getString("pseudonyme")
                ));
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

    public List<ModeleUtilisateurRecherche> getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public List<ModeleMessage> getListeMessages() {
        return listeMessages;
    }
}