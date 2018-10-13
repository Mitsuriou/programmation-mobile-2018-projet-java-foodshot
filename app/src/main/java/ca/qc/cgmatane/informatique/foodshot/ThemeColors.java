package ca.qc.cgmatane.informatique.foodshot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;

public class ThemeColors {

    private static final String NAME = "ThemeColors", KEY = "color";

    @ColorInt
    public int color;

    public ThemeColors(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String id_Color = sharedPreferences.getString(KEY, "1");

        switch (id_Color){
            case "1":
                context.setTheme(R.style.AppTheme);
                break;
            case "2":
                context.setTheme(R.style.AppThemeRouge);
                break;
            case "3":
                context.setTheme(R.style.AppThemeVert);
                break;
            case "4":
                context.setTheme(R.style.AppThemeBleu);
                break;
        }
    }

    public static void switchThemeColor(Activity activity, int id_color) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY, "" + id_color);
        editor.apply();
        activity.recreate();
    }
}