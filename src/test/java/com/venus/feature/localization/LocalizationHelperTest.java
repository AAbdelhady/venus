package com.venus.feature.localization;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalizationHelperTest {

    @Test
    public void getLocalizedValue_shouldReturnValueInProvidedLanguage_whenLanguageProvidedIsSupported() {
        // given
        final String bundleName = "dummy";
        final String key = "DUMMY";

        // when
        String nullArgResult = LocalizationHelper.getLocalizedValue(null, bundleName, key);
        String emptyArgResult = LocalizationHelper.getLocalizedValue("", bundleName, key);
        String englishArgResult = LocalizationHelper.getLocalizedValue("en", bundleName, key);
        String estonianArgResult = LocalizationHelper.getLocalizedValue("ee", bundleName, key);

        // then
        assertEquals("estonian", nullArgResult);
        assertEquals("estonian", emptyArgResult);
        assertEquals("english", englishArgResult);
        assertEquals("estonian", estonianArgResult);
    }
}