package com.venus.test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DummyUtils {

    public static long randomId() {
        return Integer.valueOf(random(0, 10000)).longValue();
    }

    public static int random(int min, int max) {
        Random r = new Random();
        return r.ints(min, max).findFirst().orElseThrow(RuntimeException::new);
    }

    public static String randomProfilePictureUrl() {
        return "https://picsum.photos/" + randomDimension() + "/" + randomDimension();
    }

    private static String randomDimension() {
        final List<Integer> allowedDimensions = Arrays.asList(200, 300, 400, 500, 600, 700, 800);
        return allowedDimensions.get(random(0, allowedDimensions.size() - 1)).toString();
    }
}
