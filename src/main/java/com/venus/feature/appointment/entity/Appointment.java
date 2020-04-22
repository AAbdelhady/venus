package com.venus.feature.appointment.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.common.entity.AuditedEntity;
import com.venus.feature.customer.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "bookings_seq_generator")
    @SequenceGenerator(name = "bookings_seq_generator", sequenceName = "bookings_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;
}
