package com.airbnb.service;

import com.airbnb.entity.Country;
import com.airbnb.payload.CountryDto;
import com.airbnb.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService{

    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDto addCountry(CountryDto countryDto) {
        Country country = mapToEntity(countryDto);
        Country savedEntity = countryRepository.save(country);
        CountryDto dto = mapToDto(savedEntity);
        return dto;
    }

    Country mapToEntity(CountryDto dto){
        Country entity=new Country();
        entity.setName(dto.getName());
        return entity;
    }

    CountryDto mapToDto(Country country){
        CountryDto dto=new CountryDto();
        dto.setId(country.getId());
        dto.setName(country.getName());
        return dto;
    }
}
