package www.minesweeper.com.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by more on 9/19/2017.
 */
public class SaveScorePreferences {

    static final String PREF_FIRST= "first";
    static final String PREF_SECOND= "second";
    static final String PREF_THIRD= "third";
    static final String PREF_SOUND= "sound";
    static final String PREF_VIBRATION= "vibration";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void savefirst(Context ctx, int score)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_FIRST, score);
        editor.commit();
    }
    public static void savesecond(Context ctx, int score)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_SECOND, score);
        editor.commit();
    }
    public static void savethird(Context ctx, int score)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_THIRD, score);
        editor.commit();
    }
    public static void savesound(Context ctx, boolean value)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_SOUND, value);
        editor.commit();
    }
    public static void savevibration(Context ctx, boolean value)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_VIBRATION, value);
        editor.commit();
    }


    public static int getfirst(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_FIRST, 0);
    }

    public static int getsecond(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_SECOND, 0);
    }

    public static int getthird(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_THIRD, 0);
    }
    public  static boolean getsound(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_SOUND, true);
    }
    public  static boolean getvibration(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_VIBRATION,true);
    }
}
