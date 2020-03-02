package com.venus.feature.localization;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

import static com.venus.feature.localization.LocalizationConstants.DEFAULT_LANG;
import static com.venus.feature.localization.LocalizationConstants.SUPPORTED_LANGS;

public class LocalizationHelper {

    private static final String RESOURCE_BUNDLE_ROOT = "i18n";

    public static String getLocalizedValue(String bundleName, String key) {
        final String baseName = String.join("/", RESOURCE_BUNDLE_ROOT, bundleName, bundleName);
        ResourceBundle values = ResourceBundle.getBundle(baseName, locale());
        return values.getString(key);
    }

    private static Locale locale() {
        final String lang = LocaleContextHolder.getLocale().getLanguage();
        return SUPPORTED_LANGS.contains(lang) ? LocaleContextHolder.getLocale() : new Locale(DEFAULT_LANG);
    }
}
