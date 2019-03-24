package com.venus.domain.entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artist extends AbstractUserEntity {

    @Column(name = "active")
    private boolean active;

    @Column(name = "artist_nick")
    private String artistNick;
}
