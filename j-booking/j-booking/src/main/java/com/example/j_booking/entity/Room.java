package com.example.j_booking.entity;

import com.example.j_booking.constants.RoomCapacity;
import com.example.j_booking.constants.RoomClass;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Hotel hotel;

    @Column(name = "room_class")
    @Enumerated(EnumType.STRING)
    RoomClass roomClass;

    @Column(name = "room_capacity")
    @Enumerated(EnumType.STRING)
    RoomCapacity roomCapacity;

    Double price;

}
