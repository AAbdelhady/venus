package com.venus.feature.localization;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.venus.feature.localization.LocalizationConstants.DEFAULT_LANG;
import static com.venus.feature.localization.LocalizationConstants.SUPPORTED_LANGS;

public class LocalizationHelper {

    private static final String RESOURCE_BUNDLE_ROOT = "i18n";

    public static String getLocalizedValue(String lang, String bundleName, String key) {
        final String baseName = String.join("/", RESOURCE_BUNDLE_ROOT, bundleName, bundleName);
        ResourceBundle values = ResourceBundle.getBundle(baseName, locale(lang));
        return values.getString(key);
    }

    private static Locale locale(String lang) {
        String language = DEFAULT_LANG;
        if (SUPPORTED_LANGS.contains(lang)) {
            language = lang;
        }
        return new Locale(language);
    }
}
