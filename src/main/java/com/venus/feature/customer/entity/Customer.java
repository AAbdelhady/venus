package com.venus.feature.customer.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.venus.feature.common.entity.AbstractExtendedUser;

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
