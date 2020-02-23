package com.venus.feature.specialty.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.common.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "specialities")
@Getter
@Setter
public class Speciality extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "specialties_seq_generator")
    @SequenceGenerator(name = "specialties_seq_generator", sequenceName = "specialties_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false, updatable = false)
    private Artist artist;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
