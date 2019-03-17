package com.venus.domain.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
public abstract class BaseEntity {

    public abstract Long getId();

    @Column(name = "created", nullable = false, updatable = false)
    @Getter
    private Instant created;

    @Column(name = "modified", nullable = false)
    @Getter
    private Instant modified;
}
