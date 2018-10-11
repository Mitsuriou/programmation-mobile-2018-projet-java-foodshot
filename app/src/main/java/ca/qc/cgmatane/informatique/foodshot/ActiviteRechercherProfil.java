package ca.qc.cgmatane.informatique.foodshot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.serveur.RechercherProfilAPI;

public class ActiviteRechercherProfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_rechercher_profil);
        gererChampRecherche();

        ListView listePseudonymesTrouves = (ListView) findViewById(R.id.liste_pseudonymes_trouves);

        List<HashMap<String, String>> listeLivres = actualiserListe();

        SimpleAdapter adaptateur = new SimpleAdapter(
                this,
                listeLivres,
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
    }

    public void gererChampRecherche() {
        final EditText champPseudonyme = (EditText) findViewById(R.id.champ_recherche_pseudonyme);
        champPseudonyme.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // TODO Faire une requete en BDD
                        // TODO puis actualiser la liste avec les données récupérées en BDD
                        new RechercherProfilAPI(charSequence.toString()).execute();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );
    }

    // TEST AFFICHAGE
    public List<HashMap<String, String>> actualiserListe() {
        List<HashMap<String,String>> listeComptes = new ArrayList<>();

        HashMap<String,String> compte;

        for (int i = 0; i<11; i++) {
            compte = new HashMap<>();
            compte.put("nom", "Dylan Jacquemin"+i);
            compte.put("pseudonyme", "@Mitsuriou"+i);
            listeComptes.add(compte);
        }

        return listeComptes;
    }


}
