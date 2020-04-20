package com.venus.util;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.venus.feature.common.dto.response.PageResponse;

public class MapperUtils {
    public static <E, R> PageResponse<R> mapPage(Page<E> page, Function<List<E>, List<R>> mapList) {
        List<R> responseList = mapList.apply(page.getContent());
        return new PageResponse<>(responseList, page.getTotalElements());
    }
}
