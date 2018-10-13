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

    public ThemeColors(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String id_Color = sharedPreferences.getString(KEY, "1");
        Log.d("COLORRRRRRRRRRRRRR", id_Color);

        switch (id_Color){
            case "1":
                context.setTheme(R.style.AppThemeRouge);
                break;
            case "2":
                context.setTheme(R.style.AppThemeVert);
                break;
            case "3":
                context.setTheme(R.style.AppThemeBleu);
                break;
        }
        //context.setTheme(context.getResources().getIdentifier("T_" + stringColor, "style", context.getPackageName()));
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