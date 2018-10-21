package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;

public class ActiviteMonProfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.PREFERENCES_THEME_COULEUR, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppThemeNoActionBar);
        }
        else {
            setTheme(R.style.AppThemeNoirNoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_mon_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modifier sa photo de profil", Snackbar.LENGTH_LONG)
                        .setAction("Suivre", null).show();
            }
        });
    }
}
