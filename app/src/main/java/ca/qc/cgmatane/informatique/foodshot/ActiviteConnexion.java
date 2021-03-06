package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.serveur.AuthentificationAPI;

public class ActiviteConnexion extends AppCompatActivity {

    private AuthentificationAPI authentificationAPI;

    private EditText champPseudonyme;
    private EditText champMdp;
    private TextView affichageErreurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.PREFERENCES_THEME_COULEUR, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.AppThemeNoir);
        }

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
                Intent intentionNaviguerVersVueCreerCompte = new Intent(getApplicationContext(), ActiviteCreationUtilisateur.class);
                startActivity(intentionNaviguerVersVueCreerCompte);
            }
        });
    }

    private void validerConnexion() {
        this.reinitialiserErreurs();

        if (isIdentificationValide()) {
            SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.PREFERENCES_GENERALES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editeur = preferencesPartagees.edit();

            editeur.putInt("id_utilisateur", authentificationAPI.getUtilisateurCourant().getIdUtilisateur());
            editeur.putString("nom", authentificationAPI.getUtilisateurCourant().getNom());
            editeur.putString("pseudonyme", authentificationAPI.getUtilisateurCourant().getPseudonyme());
            editeur.putString("url_image", authentificationAPI.getUtilisateurCourant().getUrlImage());
            editeur.putInt("nombre_mention_aime", authentificationAPI.getUtilisateurCourant().getNbrMentionAime());
            editeur.putString("creation", authentificationAPI.getUtilisateurCourant().getCreation().toString());
            editeur.apply();
            editeur.commit();

            Toast.makeText(ActiviteConnexion.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
            Intent intentionSeConnecter = new Intent(this, ActivitePrincipale.class);
            startActivity(intentionSeConnecter);
            this.finish();
        }
    }

    private void reinitialiserErreurs() {
        this.affichageErreurs.setText("");
    }

    private boolean isIdentificationValide() {
        if (this.champPseudonyme.getText().toString().equals("")) {
            this.affichageErreurs.setText("Veuillez renseigner un pseudonyme");
            return false;
        }

        authentificationAPI = new AuthentificationAPI(
                this.champPseudonyme.getText().toString(),
                this.champMdp.getText().toString()
        );

        try {
            authentificationAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (authentificationAPI.getUtilisateurCourant() == null) {
            if (authentificationAPI.getListeMessages().size() == 1) {
                for (ModeleMessage message : authentificationAPI.getListeMessages()) {
                    this.affichageErreurs.append(message.getMessage());
                }
            }
            else {
                for (ModeleMessage message : authentificationAPI.getListeMessages()) {
                    this.affichageErreurs.append(message.getMessage() + "\n");
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
