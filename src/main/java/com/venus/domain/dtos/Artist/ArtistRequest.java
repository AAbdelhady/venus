package com.venus.domain.dtos.Artist;

import lombok.Data;

@Data
public class ArtistRequest {

    private boolean active;

    private String artistNick;
}
