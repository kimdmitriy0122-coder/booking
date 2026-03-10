package com.example.j_booking.entity;

import com.example.j_booking.constants.City;
import com.example.j_booking.constants.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "address", schema = "booking")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    private Country country;
    @Enumerated(EnumType.STRING)
    private City city;
    private String street;
    @Column(name = "building_number")
    private String buildingNumber;
}
