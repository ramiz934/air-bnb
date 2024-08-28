package com.airbnb.payload;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter

public class PropertyDto {

    private Long id;

    private String name;

    private Integer numberOfGuests;

    private Integer numberOfBeds;

    private Integer numberOfBathrooms;

    private Integer numberOfBedrooms;

    private Country country;

    private City city;


}