package com.michael.afrivac;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageHelper {
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
}
