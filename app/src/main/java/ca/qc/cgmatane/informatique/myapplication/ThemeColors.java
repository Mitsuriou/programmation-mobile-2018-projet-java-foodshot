package ca.qc.cgmatane.informatique.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.Log;

public class ThemeColors {

    private static final String NAME = "ThemeColors", KEY = "color";

    @ColorInt
    public int color;

    public ThemeColors(Context context, String packageName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String id_Color = sharedPreferences.getString(KEY, "1");

        switch (id_Color){
            case "1":
                    context.setTheme(R.style.AppTheme);
                break;

            case "2":

                if(packageName.equals("ca.qc.cgmatane.informatique.foodshot.drawer")){
                    context.setTheme(R.style.AppThemeRougeNoBar);
                }
                else {
                    context.setTheme(R.style.AppThemeRougeBar);
                }
                break;

            case "3":

                if(packageName.equals("ca.qc.cgmatane.informatique.foodshot.drawer")){
                    context.setTheme(R.style.AppThemeVertNoBar);
                }
                else {
                    context.setTheme(R.style.AppThemeVertBar);
                }
                break;

            case "4":

                if(packageName.equals("ca.qc.cgmatane.informatique.foodshot.drawer")){
                    context.setTheme(R.style.AppThemeBleuNoBar);
                }
                else {
                    context.setTheme(R.style.AppThemeBleuBar);
                }

                break;
        }
    }

    public static void switchThemeColor(Activity activity, int id_color) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY, "" + id_color);
        editor.apply();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) activity.recreate();
        else {
            Intent i = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(i);
        }
    }
}