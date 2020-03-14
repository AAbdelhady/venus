package com.venus.feature.artist.dto.response;

import com.venus.feature.artist.entity.Category;

import lombok.Data;

@Data
public class CategoryResponse {
    private Category value;
    private String text;
    private String photoUrl;
}
