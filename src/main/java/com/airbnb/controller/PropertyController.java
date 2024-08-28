package com.airbnb.controller;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import com.airbnb.payload.PropertyDto;
import com.airbnb.repository.CityRepository;
import com.airbnb.repository.CountryRepository;
import com.airbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
  
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private PropertyService propertyService;
    public PropertyController(CityRepository cityRepository, CountryRepository countryRepository, PropertyService propertyService) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<PropertyDto> createProperty(
            @RequestParam long cityId,
            @RequestParam long countryId,
            @RequestBody PropertyDto propertyDto
    ){
        City city = cityRepository.findById(cityId).get();
        Country country = countryRepository.findById(countryId).get();
        propertyDto.setCity(city);
        propertyDto.setCountry(country);
        PropertyDto dto = propertyService.createProperty(propertyDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
