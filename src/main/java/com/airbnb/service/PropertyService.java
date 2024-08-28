package com.airbnb.service;

import com.airbnb.payload.CityDto;
import com.airbnb.payload.CountryDto;
import com.airbnb.payload.PropertyDto;

public interface PropertyService {


    PropertyDto createProperty(PropertyDto propertyDto);
}
