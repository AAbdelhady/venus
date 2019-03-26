package com.venus.domain.entities.user;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class AbstractExtendedUser {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        this.id = user.getId();
        this.user = user;
    }
}
