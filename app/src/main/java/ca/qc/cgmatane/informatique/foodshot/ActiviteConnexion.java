package ca.qc.cgmatane.informatique.foodshot;

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
                Toast.makeText(ActiviteConnexion.this,
                        "Tentative de connexion...",
                        Toast.LENGTH_SHORT).show();
                validerConnexion();
            }
        });
    }

    private void validerConnexion() {
        this.reinitialiserErreurs();
        this.checkErreursDansFormulaire();
    }

    private void reinitialiserErreurs() {
        this.affichageErreurs.setText("");
    }

    private void checkErreursDansFormulaire() {
        if (!isMotDePasseValide()) {
            this.affichageErreurs.setText("Le mot de passe doit faire plus de 5 caractères");
        }
    }

    private boolean isMotDePasseValide() {
        return (champMdp.getText().length() >= 6);
    }
}
