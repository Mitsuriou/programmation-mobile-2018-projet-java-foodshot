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

    private EditText champPseudonyme;
    private EditText champMdp;
    private TextView affichageErreurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_connexion);

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

        Button boutonCreerCompte = (Button) findViewById(R.id.bouton_creer_nouveau_compte);
        boutonCreerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionNaviguerVersVueCreerCompte = new Intent(getApplicationContext(), ActiviteCreationCompte.class);
                startActivity(intentionNaviguerVersVueCreerCompte);
                finish();
            }
        });
    }

    private void validerConnexion() {
        this.reinitialiserErreurs();

        if (isIdentificationValide()) {
            Toast.makeText(ActiviteConnexion.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
            Intent intentionSeConnecter = new Intent(this, ActivitePrincipale.class);
            startActivity(intentionSeConnecter);
            this.finish();
        }
        else {
            this.affichageErreurs.setText("");
        }
    }

    private void reinitialiserErreurs() {
        this.affichageErreurs.setText("");
    }

    private boolean isIdentificationValide() {
        // TODO : call l'API pour savoir si les identifiants sont corrects
        return true;
    }
}
