package com.venus.domain.entities.user;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
abstract class AbstractExtendedUser {

    @Id
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "user_id")
    @Setter
    private User user;
}
