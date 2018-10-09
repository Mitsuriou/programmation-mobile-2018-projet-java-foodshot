package ca.qc.cgmatane.informatique.foodshot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActiviteConnexion extends AppCompatActivity {

    private EditText champNom;
    private EditText champPseudonyme;
    private EditText champMdp;
    private TextView affichageErreurs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_connexion);

        this.champNom = (EditText) findViewById(R.id.connexion_champ_nom);
        this.champPseudonyme = (EditText) findViewById(R.id.connexion_champ_pseudoynme);
        this.champMdp = (EditText) findViewById(R.id.connexion_champ_mot_de_passe);
        this.affichageErreurs = (TextView) findViewById(R.id.connexion_affichage_erreurs);

        Button boutonSeConnecter = (Button) findViewById(R.id.bouton_connexion);
        boutonSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validerConnexion();
            }
        });
    }

    private void validerConnexion() {
        this.reinitialiserErreurs();

        if (this.affichageErreurs.getText().equals("") && isNomValide() && isPseudonymeValide() && isMotDePasseValide()) {
            // TODO : CONNECTION AU SERV ??????
            // TODO: APPEL A L'API ?

            Toast.makeText(ActiviteConnexion.this,
                    "Nom : " + this.champNom.getText().toString()
                    + ", Pseudonyme : " + this.champPseudonyme.getText().toString()
                    + ", Mdp clair : " + this.champMdp.getText().toString(),
                    Toast.LENGTH_SHORT).show();

            Intent intentionSeConnecter = new Intent(this, ActivitePrincipale.class);
            startActivity(intentionSeConnecter);
            this.finish();
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
        // TODO SAVOIR SI LE PSEUDONYME EXISTE DEJA EN BDD, ET RETURN FALSE
        /*if () {
            this.affichageErreurs.setText("Le pseudonyme choisi existe déjà. Veuillez en choisir un autre.");
            return false;
        }*/

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
        if (champMdp.getText().length() < 5) {
            this.affichageErreurs.setText("Le mot de passe doit faire plus de 5 caractères");
            return false;
        }
        return true;
    }
}
