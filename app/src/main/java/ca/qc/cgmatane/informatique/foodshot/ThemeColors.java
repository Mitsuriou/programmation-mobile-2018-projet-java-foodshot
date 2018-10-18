package ca.qc.cgmatane.informatique.foodshot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;

public class ThemeColors {

    @ColorInt
    public int color;

    public ThemeColors(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.COULEURS_PREFERENCES, Context.MODE_PRIVATE);
        int id_Color = sharedPreferences.getInt("theme", 1);

        switch (id_Color){
            case 1:
                context.setTheme(R.style.AppTheme);
                break;
            case 2:
                context.setTheme(R.style.AppThemeNoir);
                break;
        }
    }

    public static void switchThemeColor(Activity activity, int id_color) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(Constantes.COULEURS_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putInt("theme", id_color);
        editor.apply();
        activity.recreate();
    }
}