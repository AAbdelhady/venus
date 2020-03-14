package com.venus.testutils;

import java.util.Random;

import com.venus.feature.artist.entity.Category;
import com.venus.feature.booking.entity.BookingStatus;
import com.venus.feature.common.enums.AuthProvider;

public class RandomUtils {

    private static final String ALPHABET_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERALS_STRING = "0123456789";

    public static String randomName() {
        return randomAlphabeticString(random(6, 16));
    }

    public static String randomEmail(String name) {
        String domain = randomAlphabeticString(6);
        return name + "@" + domain + "com";
    }

    public static String randomAlphabeticString(int length) {
        return randomString(length, ALPHABET_STRING);
    }

    public static String randomNumericString(int length) {
        return randomString(length, NUMERALS_STRING);
    }

    public static String randomString(int length, String source) {
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int) (Math.random() * source.length());
            builder.append(source.charAt(character));
        }
        return builder.toString();
    }

    public static long randomId() {
        return randomLong(0, 10000);
    }

    public static long randomLong(int min, int max) {
        return Integer.valueOf(random(min, max)).longValue();
    }

    public static int random(int min, int max) {
        Random r = new Random();
        return r.ints(min, max).findFirst().orElseThrow(RuntimeException::new);
    }

    public static Category randomCategory() {
        Category[] categories = Category.values();
        return categories[random(0, categories.length)];
    }

    public static AuthProvider randomAuthProvider() {
        AuthProvider[] providers = AuthProvider.values();
        return providers[random(0, providers.length)];
    }

    public static BookingStatus randomBookingStatus() {
        BookingStatus[] statuses = BookingStatus.values();
        return statuses[random(0, statuses.length)];
    }
}
