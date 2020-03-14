package com.venus.feature.artist.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.dto.request.ArtistRequest;
import com.venus.feature.artist.dto.response.ArtistProfileResponse;
import com.venus.feature.artist.dto.response.ArtistResponse;
import com.venus.feature.artist.dto.response.CategoryResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.artist.mapper.ArtistMapper;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.assets.AssetsHelper;
import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.common.enums.Role;
import com.venus.feature.specialty.service.SpecialityService;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import lombok.RequiredArgsConstructor;

import static com.venus.feature.localization.LocalizationConstants.BUNDLE_NAMES;
import static com.venus.feature.localization.LocalizationHelper.getLocalizedValue;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final UserService userService;
    private final SpecialityService specialityService;
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final AssetsHelper assetsHelper;

    public ArtistResponse createArtist(ArtistRequest request) {
        User user = userService.updateAuthorizedUserRole(Role.ARTIST);
        Artist artist = new Artist();
        artist.setUser(user);
        artist.setCategory(request.getCategory());
        artist = artistRepository.save(artist);
        specialityService.addSpecialitiesToArtist(request.getSpecialities(), artist);
        return artistMapper.mapOne(artist);
    }

    public ArtistProfileResponse findArtistById(Long id) {
        Artist artist = artistRepository.findByUserId(id).orElseThrow(NotFoundException::new);
        return artistMapper.mapFull(artist);
    }

    public PageResponse<ArtistResponse> searchArtists(Category category, Pageable pageable) {
        Page<Artist> page = category == null ? artistRepository.findAll(pageable) : artistRepository.findByCategory(category, pageable);
        return artistMapper.mapPage(page);
    }

    public List<CategoryResponse> listCategories() { // TODO Server side cached
        Map<Category, String> categoryTextMap = Arrays.stream(Category.values()).collect(Collectors.toMap(c -> c, c -> getLocalizedValue(BUNDLE_NAMES.CATEGORY, c.name())));
        return Arrays.stream(Category.values()).map(c -> {
            CategoryResponse response = new CategoryResponse();
            response.setValue(c);
            response.setText(categoryTextMap.get(c));
            response.setPhotoUrl(assetsHelper.getCategoryPhotoUrl(c));
            return response;
        }).collect(Collectors.toList());
    }
}
