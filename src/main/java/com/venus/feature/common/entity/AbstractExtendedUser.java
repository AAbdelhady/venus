package com.venus.feature.common.entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.venus.feature.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
public abstract class AbstractExtendedUser {

    @Id
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "user_id")
    @Setter
    private User user;
}
