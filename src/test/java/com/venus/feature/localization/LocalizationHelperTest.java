package com.venus.feature.localization;

import java.util.Locale;

import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import static org.junit.Assert.assertEquals;

public class LocalizationHelperTest {

    @Test
    public void getLocalizedValue_shouldReturnValueInProvidedLanguage_whenLanguageProvidedIsSupported() {
        // given
        final String bundleName = "dummy";
        final String key = "DUMMY";

        // when
        LocaleContextHolder.setLocale(new Locale(""));
        String emptyLangResult = LocalizationHelper.getLocalizedValue(bundleName, key);

        LocaleContextHolder.setLocale(new Locale("unsupported"));
        String unsupportedLangResult = LocalizationHelper.getLocalizedValue(bundleName, key);

        LocaleContextHolder.setLocale(new Locale(LocalizationConstants.EN));
        String englishLangResult = LocalizationHelper.getLocalizedValue(bundleName, key);

        LocaleContextHolder.setLocale(new Locale(LocalizationConstants.ET));
        String estonianLangResult = LocalizationHelper.getLocalizedValue(bundleName, key);

        // then
        assertEquals("estonian", emptyLangResult);
        assertEquals("estonian", unsupportedLangResult);
        assertEquals("english", englishLangResult);
        assertEquals("estonian", estonianLangResult);
    }
}