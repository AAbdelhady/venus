package com.venus.feature.artist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.venus.feature.common.entity.AbstractExtendedUser;

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
public class Artist extends AbstractExtendedUser {


    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;


    @Column(name = "active")
    private boolean active;
}
