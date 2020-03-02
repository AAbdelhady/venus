package com.venus.feature.assets;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.venus.IntegrationTest;
import com.venus.feature.artist.entity.Category;

import static org.junit.Assert.assertEquals;

public class AssetsHelperIntegrationTest extends IntegrationTest {

    @Autowired
    private AssetsHelper assetsHelper;

    @Test
    public void getCategoryPhotoUrl_shouldReturnCorrectUrl() {
        // when
        String photoUrl = assetsHelper.getCategoryPhotoUrl(Category.TATTOO);

        // then
        assertEquals("http://localhost:8080/api/image/category/TATTOO.jpg", photoUrl);
    }
}