package com.venus.test;

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
}
