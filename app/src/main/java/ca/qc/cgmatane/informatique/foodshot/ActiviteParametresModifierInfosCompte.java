package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.serveur.ModifierUtilisateurAPI;

public class ActiviteParametresModifierInfosCompte extends AppCompatActivity {

    private EditText compteChampNomUtilisateur;
    private EditText compteChampMdp1;
    private EditText compteChampMdp2;
    private TextView compteAffichageErreurs;

    private SharedPreferences preferencesPartagees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.COULEURS_PREFERENCES, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.AppThemeNoir);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_parametres_modifier_infos_compte);

        this.compteChampNomUtilisateur = (EditText) findViewById(R.id.param_modifier_infos_compte_champ_nom);
        this.compteChampMdp1 = (EditText) findViewById(R.id.param_modifier_infos_compte_champ_mdp_1);
        this.compteChampMdp2 = (EditText) findViewById(R.id.param_modifier_infos_compte_champ_mdp_2);
        this.compteAffichageErreurs = (TextView) findViewById(R.id.param_compte_affichage_erreurs);

        preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);
        this.compteChampNomUtilisateur.setText(preferencesPartagees.getString("nom", ""));

        Button boutonValiderModifications = (Button) findViewById(R.id.bouton_param_valider_modification_infos_compte);
        boutonValiderModifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validerModifications();
            }
        });
    }

    private void validerModifications() {
        this.reinitialiserErreurs();

        if (isNomValide() && isMotDePasseValide()) {
            SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);
            new ModifierUtilisateurAPI(
                    preferencesPartagees.getInt("id_utilisateur", -1),
                    this.compteChampNomUtilisateur.getText().toString(),
                    this.compteChampMdp1.getText().toString()).execute();

            Intent intentionNaviguerVersVueParametres = new Intent(this, ActiviteParametres.class);
            startActivity(intentionNaviguerVersVueParametres);
            this.finish();
        }
    }

    private void reinitialiserErreurs() {
        this.compteAffichageErreurs.setText("");
    }

    private boolean isNomValide() {
        if (this.compteChampNomUtilisateur.getText().toString().length() < 4) {
            this.compteAffichageErreurs.setText("Votre nom doit être composé de 4 lettres et chiffres ou plus");
            return false;
        }
        if (!this.compteChampNomUtilisateur.getText().toString().matches("[A-Za-z0-9]*")) {
            this.compteAffichageErreurs.setText("N'utilisez que des lettres et chiffres pour composer votre nom.");
            return false;
        }
        return true;
    }

    private boolean isMotDePasseValide() {
        if (this.compteChampMdp1.getText().length() < 5 || this.compteChampMdp2.getText().length() <5) {
            this.compteAffichageErreurs.setText("Le mot de passe doit faire plus de 5 caractères.");
            return false;
        }
        // TODO : changer ce bloc pour comparer les Hash des mots de passe :
        if (!this.compteChampMdp1.getText().toString().equals(this.compteChampMdp2.getText().toString())) {
            this.compteAffichageErreurs.setText("Les mots de passe ne correspondent pas.");
            return false;
        }
        return true;
    }
}
