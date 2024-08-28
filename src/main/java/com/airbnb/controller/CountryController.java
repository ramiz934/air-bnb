package com.airbnb.controller;


import com.airbnb.payload.CountryDto;
import com.airbnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    @PostMapping
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto countryDto) {
        CountryDto dto = countryService.addCountry(countryDto);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

}
