package ca.qc.cgmatane.informatique.foodshot.serveur;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecevoirPublicationAPI extends AsyncTask<String, String, String> {

    private int compteur;
    private int dernierId;
    private int idUtilisateur;
    private boolean statut;
    private boolean progress;
    private boolean publicationPerso;
    private List<ModelePublication> listePublication;
    private List<ModeleMessage> listeMessages;

    public RecevoirPublicationAPI(int idUtilisateur, int compteur) {
        this.dernierId = -1;
        this.compteur = compteur;
        this.idUtilisateur = idUtilisateur;
        this.publicationPerso = false;
        this.progress = true;
    }

    public RecevoirPublicationAPI(int idUtilisateur, int compteur, boolean publicationPerso) {
        this.dernierId = -1;
        this.compteur = compteur;
        this.idUtilisateur = idUtilisateur;
        this.publicationPerso = publicationPerso;
        this.progress = true;
    }

    public RecevoirPublicationAPI(int idUtilisateur, int compteur, int dernierId) {
        this.compteur = compteur;
        this.dernierId = dernierId;
        this.idUtilisateur = idUtilisateur;
        this.publicationPerso = false;
        this.progress = true;
    }

    public RecevoirPublicationAPI(int idUtilisateur, int compteur, int dernierId, boolean publicationPerso) {
        this.compteur = compteur;
        this.dernierId = dernierId;
        this.idUtilisateur = idUtilisateur;
        this.publicationPerso = publicationPerso;
        this.progress = true;
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
                .url("http://54.37.152.134/api/publication/lire_pagination.php?id_utilisateur=" + idUtilisateur + "&page=" + compteur + "&dernierId=" + dernierId + "&publication_perso=" + publicationPerso)
                .build();

        try {
            reponse = client.newCall(request).execute();

            String jsonDonneesString = reponse.body().string();
            JSONObject jsonDonneesObjet = new JSONObject(jsonDonneesString);

            // statut
            String statutString = jsonDonneesObjet.getString("statut");
            this.statut = statutString.equals("true");

            // donnee
            String donneeString = jsonDonneesObjet.getString("donnee");
            JSONObject jsonObjectDonnee = new JSONObject(donneeString);

            String publicationString = jsonObjectDonnee.getString("publication");
            JSONArray publicationJsonArray = new JSONArray(publicationString);

            List<JSONObject> listeDesPublicationsJson = new ArrayList<>();
            for (int i = 0; i < publicationJsonArray.length(); i++) {
                listeDesPublicationsJson.add(publicationJsonArray.getJSONObject(i));
            }

            this.listePublication = new ArrayList<>();
            for (JSONObject valeur : listeDesPublicationsJson) {

                String urlImage = valeur.getString("url_image");
                if ((urlImage != null) || (urlImage.equals(""))) {
                    urlImage = "https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg";
                }

                String urlProfil = valeur.getString("url_image_utilisateur");
                if ((urlProfil == null) || (urlProfil.equals(""))) {
                    urlProfil = "https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg";
                }

                this.listePublication.add(new ModelePublication(
                        valeur.getInt("id_publication"),
                        valeur.getString("titre"),
                        valeur.getString("description"),
                        urlImage,
                        valeur.getDouble("latitude"),
                        valeur.getDouble("longitude"),
                        valeur.getBoolean("j_aime"),
                        valeur.getInt("nombre_mention_aime"),
                        valeur.getInt("id_utilisateur"),
                        valeur.getString("pseudonyme_utilisateur"),
                        urlProfil,
                        valeur.getString("creation")
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
            for (JSONObject valeur : listeDesMessages) {
                this.listeMessages.add(new ModeleMessage(
                        valeur.getInt("code"),
                        valeur.getString("type"),
                        valeur.getString("message")
                ));
            }
        } catch (Exception e) {
            this.progress = false;
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

    public List<ModelePublication> getListePublication() {
        return listePublication;
    }

    public List<ModeleMessage> getListeMessages() {
        return listeMessages;
    }

    public boolean getProgress() {
        return this.progress;
    }
}