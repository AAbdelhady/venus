package com.venus.domain.entities.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class Customer extends AbstractExtendedUser {
}
