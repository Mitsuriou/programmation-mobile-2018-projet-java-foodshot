package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;

public class ActiviteMonProfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.PREFERENCES_THEME_COULEUR, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.AppThemeNoir);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_mon_profil);

    }
}
