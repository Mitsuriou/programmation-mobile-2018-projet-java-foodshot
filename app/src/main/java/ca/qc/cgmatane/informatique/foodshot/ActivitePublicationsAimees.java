package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;

public class ActivitePublicationsAimees extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.PREFERENCES_THEME_COULEUR, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.AppThemeNoir);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_publications_aimees);
    }
}
