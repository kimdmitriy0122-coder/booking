package com.example.j_booking.entity;

import com.example.j_booking.constants.RoomCapacity;
import com.example.j_booking.constants.RoomClass;
import com.example.j_booking.constants.RoomStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rooms", schema = "booking")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "hotel_id")
//    private Hotel hotel;
    @Column(nullable = false, name = "hotel_id")
    private Long hotelId;

    @Column(name = "room_class")
    @Enumerated(EnumType.STRING)
    RoomClass roomClass;

    @Column(name = "room_capacity")
    @Enumerated(EnumType.STRING)
    RoomCapacity roomCapacity;

}
