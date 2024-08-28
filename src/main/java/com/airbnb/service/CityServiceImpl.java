package com.airbnb.service;

import com.airbnb.entity.City;
import com.airbnb.payload.CityDto;
import com.airbnb.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService{

    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDto addCity(CityDto cityDto) {
        City city = mapToEntity(cityDto);
        City savedEntity = cityRepository.save(city);
        CityDto dto = mapToDto(savedEntity);
        return dto;
    }
    City mapToEntity(CityDto dto){
        City entity=new City();
        entity.setName(dto.getName());
        return entity;
    }
   CityDto mapToDto(City city){
        CityDto dto=new CityDto();
        dto.setId(city.getId());
        dto.setName(city.getName());
        return dto;
   }
}
