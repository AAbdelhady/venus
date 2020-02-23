package com.venus.feature.assets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.venus.feature.artist.entity.Category;

@Component
public class AssetsHelper {

    private static final String imageFolder = "/image";
    private static final String categoryImageFolder = "/category/";

    @Value("${backend.base-url:}")
    private String host;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    public String getCategoryPhotoUrl(Category category) {
        return host + contextPath + imageFolder + categoryImageFolder + category + ".jpg";
    }
}
