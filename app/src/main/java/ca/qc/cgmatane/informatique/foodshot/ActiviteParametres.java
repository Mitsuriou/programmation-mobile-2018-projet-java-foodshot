package ca.qc.cgmatane.informatique.foodshot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActiviteParametres extends AppCompatActivity {

    private EditText compteChampNomUtilisateur;
    private EditText compteChampMdp1;
    private EditText compteChampMdp2;
    private TextView compteAffichageErreurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_parametres);

        Button boutonParamCompte = (Button) findViewById(R.id.bouton_param_compte);
        boutonParamCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afficherVueCompte();
            }
        });
    }

    private void afficherVueCompte() {
        setContentView(R.layout.param_modifier_infos_compte);
        setTitle(getResources().getString(R.string.titre_param_modifier_infos_compte));

        this.compteChampNomUtilisateur = (EditText) findViewById(R.id.param_modifier_infos_compte_champ_nom);
        this.compteChampMdp1 = (EditText) findViewById(R.id.param_modifier_infos_compte_champ_mdp_1);
        this.compteChampMdp2 = (EditText) findViewById(R.id.param_modifier_infos_compte_champ_mdp_2);

        this.compteAffichageErreurs = (TextView) findViewById(R.id.param_compte_affichage_erreurs);

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
            /*new ModifierUtilisateurAPI(this.compteChampNom.getText().toString(),
                    ).execute();*/

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
