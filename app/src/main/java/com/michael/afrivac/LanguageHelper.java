package com.michael.afrivac;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class LanguageHelper {
    private static final String GENERAL_STORAGE = "GENERAL_STORAGE";
    private static final String KEY_USER_LANGUAGE = "KEY_USER_LANGUAGE";

    public static void changeLocale(Resources res, String locale) {
        Configuration configuration;
        configuration = new Configuration(res.getConfiguration());

        switch (locale) {
            case "es":
                configuration.setLocale(new Locale("es"));
                break;
            case "fr":
                configuration.setLocale(Locale.FRENCH);
                break;
            default:
                configuration.setLocale(Locale.ENGLISH);
        }
        res.updateConfiguration(configuration, res.getDisplayMetrics());
    }

    public static void storeUserLanguage(Context context, String locale) {
        context.getSharedPreferences(GENERAL_STORAGE, MODE_PRIVATE)
                .edit()
                .putString(KEY_USER_LANGUAGE, locale)
                .apply();
    }

    /**
     * @return The stored user language or null if not found.
     */
    public static String getUserLanguage(Context context) {
        return context.getSharedPreferences(GENERAL_STORAGE, MODE_PRIVATE)
                .getString(KEY_USER_LANGUAGE, null);
    }
}
