package com.venus.feature.booking.offering.entity;

import java.time.Instant;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.venus.feature.booking.core.entity.Booking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "offerings")
@Getter
@Setter
@NoArgsConstructor
public class Offering {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "offerings_seq_generator")
    @SequenceGenerator(name = "offerings_seq_generator", sequenceName = "offerings_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "created", nullable = false, updatable = false)
    @Getter
    @CreationTimestamp
    private Instant created;

    public Offering(Booking booking, LocalTime time) {
        this.booking = booking;
        this.time = time;
    }
}
