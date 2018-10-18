package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleUtilisateurRecherche;
import ca.qc.cgmatane.informatique.foodshot.serveur.RechercherProfilAPI;

public class ActiviteRechercherProfil extends AppCompatActivity {

    private ListView listePseudonymesTrouves;
    private SimpleAdapter adaptateur;
    protected TextView affichageTexteResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.COULEURS_PREFERENCES, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.AppThemeNoir);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_rechercher_profil);
        gererChampRecherche();

        this.affichageTexteResultat = (TextView) findViewById(R.id.rechercher_profil_affichage_texte_resultat);
        this.listePseudonymesTrouves = (ListView) findViewById(R.id.liste_pseudonymes_trouves);
    }

    public void gererChampRecherche() {
        final EditText champPseudonyme = (EditText) findViewById(R.id.champ_recherche_pseudonyme);
        champPseudonyme.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (champPseudonyme.getText().toString().equals(""))
                            return;
                        foo(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {}
                }
        );
    }

    public void foo(CharSequence pseudoAChercher) {
        RechercherProfilAPI rechercherProfilAPI = new RechercherProfilAPI(pseudoAChercher.toString());

        try {
            rechercherProfilAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.affichageTexteResultat.setText("");

        if (!rechercherProfilAPI.isStatut()) {
            this.affichageTexteResultat.setText("Une erreur s'est produite");
            return;
        }

        for (ModeleMessage modeleMessage : rechercherProfilAPI.getListeMessages()) {
            this.affichageTexteResultat.append(modeleMessage.getMessage() + "\n");
        }

        this.actualiserListe(rechercherProfilAPI.getListeUtilisateurs());
    }

    public List<HashMap<String, String>> actualiserListe(List<ModeleUtilisateurRecherche> listeDesUtilisateurs) {
        List<HashMap<String,String>> listeComptes = new ArrayList<>();
        HashMap<String,String> compte;

        for (ModeleUtilisateurRecherche modeleUtilisateur : listeDesUtilisateurs) {
            compte = new HashMap<>();
            compte.put("nom", modeleUtilisateur.getNom());
            compte.put("pseudonyme", "@" + modeleUtilisateur.getPseudonyme());
            listeComptes.add(compte);
        }

        adaptateur = new SimpleAdapter(
                this,
                listeComptes,
                android.R.layout.two_line_list_item,
                new String[] {"nom","pseudonyme"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        listePseudonymesTrouves.setAdapter(adaptateur);

        listePseudonymesTrouves.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View vue, int positionDansAdapteur, long positionItem) {
                        ListView vueListeComptes = (ListView)vue.getParent();

                        @SuppressWarnings("unchecked")
                        HashMap<String,String> compte = (HashMap<String, String>) vueListeComptes.getItemAtPosition((int)positionItem);

                        Toast.makeText(getApplicationContext(),
                                "Position "+positionItem + " pseudo " + compte.get("pseudonyme"),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        adaptateur.notifyDataSetChanged();

        return listeComptes;
    }

}
