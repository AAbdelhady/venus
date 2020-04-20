package com.venus.util;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;

public class CacheControlUtils {
    public static CacheControl maxAgeMinutes(long maxAgeInMinutes) {
        return CacheControl.maxAge(maxAgeInMinutes, TimeUnit.MINUTES).mustRevalidate();
    }
}
