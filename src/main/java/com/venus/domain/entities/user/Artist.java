package com.venus.domain.entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
public class Artist {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "active")
    private boolean active;

    @Column(name = "artist_nick")
    private String artistNick;
}
