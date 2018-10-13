package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.serveur.SupprimerUtilisateurAPI;

public class ActiviteParametres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ThemeColors(this);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_parametres);

        Button boutonParamCompte = (Button) findViewById(R.id.bouton_param_compte);
        boutonParamCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActiviteParametresModifierInfosCompte.class));
            }
        });

        Button boutonSupprimerMonCompte = (Button) findViewById(R.id.bouton_suppr_compte);
        boutonSupprimerMonCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afficherDialogueSuppresion();
            }
        });
    }

    public void deconnexion() {
        SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editeur = preferencesPartagees.edit();
        editeur.clear();
        editeur.apply();
        editeur.commit();
        Toast.makeText(this, "Compte supprimé", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ActiviteConnexion.class));
    }

    public void afficherDialogueSuppresion() {
        final SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Suppression de votre compte FoodShot")
                .setMessage("Voulez-vous vraiment supprimer votre compte FoodShot ? Vos données seront intégralement effacées.")
                .setPositiveButton("Supprimer mon compte", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new SupprimerUtilisateurAPI(preferencesPartagees.getInt("id_utilisateur", -1)).execute();
                        deconnexion();
                    }
                })
                .setNegativeButton("Non !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                })
                .show();
    }
}
