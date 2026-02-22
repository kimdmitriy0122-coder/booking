package com.example.j_booking.entity;

import com.example.j_booking.constants.RoomCapacity;
import com.example.j_booking.constants.RoomClass;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rooms", schema = "booking", indexes = {
        @Index(name = "idx_room_id_unq", columnList = "id", unique = true)
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "hotel_id")
    private Long hotelId;

    @Column(name = "room_class")
    @Enumerated(EnumType.STRING)
    RoomClass roomClass;

    @Column(name = "room_capacity")
    @Enumerated(EnumType.STRING)
    RoomCapacity roomCapacity;

}
