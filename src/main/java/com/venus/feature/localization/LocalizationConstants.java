package com.venus.feature.localization;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public final class LocalizationConstants {
    public static final String ACCEPT_LANGUAGE_HEADER = "accept-language";
    public static final String EN = "en";
    public static final String EE = "ee";
    public static final String RU = "ru";
    public static final String DEFAULT_LANG = EE;
    public static final Set<String> SUPPORTED_LANGS = ImmutableSet.of(EN, EE, RU);

    public static final class BUNDLE_NAMES {
        public static final String CATEGORY = "category";
    }
}
