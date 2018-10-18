package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.serveur.CreerUtilisateurAPI;

public class ActiviteCreationUtilisateur extends AppCompatActivity {

    private EditText champNom;
    private EditText champPseudonyme;
    private EditText champMdp;
    private EditText champMdp2;
    private TextView affichageErreurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.COULEURS_PREFERENCES, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.AppThemeNoir);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_creation_compte);

        this.champNom = (EditText) findViewById(R.id.creation_compte_champ_nom);
        this.champPseudonyme = (EditText) findViewById(R.id.creation_compte_champ_pseudoynme);
        this.champMdp = (EditText) findViewById(R.id.creation_compte_champ_mot_de_passe);
        this.champMdp2 = (EditText) findViewById(R.id.creation_compte_champ_mot_de_passe_2);
        this.affichageErreurs = (TextView) findViewById(R.id.creation_compte_affichage_erreurs);

        Button boutonSeCreationCompte = (Button) findViewById(R.id.bouton_creation_compte);
        boutonSeCreationCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validerCreationCompte();
            }
        });
    }

    private void validerCreationCompte() {
        this.reinitialiserErreurs();

        if (isNomValide() && isPseudonymeValide() && isMotDePasseValide()) {
            CreerUtilisateurAPI creerUtilisateurAPI = new CreerUtilisateurAPI(
                    this.champNom.getText().toString(),
                    this.champPseudonyme.getText().toString(),
                    this.champMdp.getText().toString()
            );

            try {
                creerUtilisateurAPI.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (creerUtilisateurAPI.getListeMessages().size() == 1) {
                for (ModeleMessage message : creerUtilisateurAPI.getListeMessages()) {
                    this.affichageErreurs.append(message.getMessage());
                }
            }
            else {
                for (ModeleMessage message : creerUtilisateurAPI.getListeMessages()) {
                    this.affichageErreurs.append(message.getMessage() + "\n");
                }
            }

        }
    }

    private void reinitialiserErreurs() {
        this.affichageErreurs.setText("");
    }

    private boolean isNomValide() {
        if (this.champNom.getText().toString().length() < 4) {
            this.affichageErreurs.setText("Votre nom doit être composé de 4 lettres et chiffres ou plus");
            return false;
        }
        if (!this.champNom.getText().toString().matches("[A-Za-z0-9]*")) {
            this.affichageErreurs.setText("N'utilisez que des lettres et chiffres pour composer votre nom.");
            return false;
        }
        return true;
    }

    private boolean isPseudonymeValide() {
        if (this.champPseudonyme.getText().toString().length() < 4) {
            this.affichageErreurs.setText("Votre pseudonyme doit être composé de 4 lettres et chiffres ou plus");
            return false;
        }
        if (!this.champPseudonyme.getText().toString().matches("[A-Za-z0-9]*")) {
            this.affichageErreurs.setText("N'utilisez que des lettres et chiffres pour composer votre pseudonyme.");
            return false;
        }
        return true;
    }

    private boolean isMotDePasseValide() {
        if (champMdp.getText().length() < 5 || champMdp2.getText().length() <5) {
            this.affichageErreurs.setText("Le mot de passe doit faire plus de 5 caractères.");
            return false;
        }
        // TODO : changer ce bloc pour comparer les Hash des mots de passe :
        if (!this.champMdp.getText().toString().equals(this.champMdp2.getText().toString())) {
            this.affichageErreurs.setText("Les mots de passe ne correspondent pas.");
            return false;
        }
        return true;
    }
}
