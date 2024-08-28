package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.payload.PropertyDto;
import com.airbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService{
    @Autowired
    private PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public PropertyDto createProperty(PropertyDto propertyDto) {
        Property property = mapToEntity(propertyDto);
        Property savedEntity = propertyRepository.save(property);
        PropertyDto dtos = mapToDto(savedEntity);
        return dtos;
    }

    Property mapToEntity(PropertyDto dto) {
        Property entity=new Property();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setNumberOfGuests(dto.getNumberOfGuests());
        entity.setNumberOfBeds(dto.getNumberOfBeds());
        entity.setNumberOfBathrooms(dto.getNumberOfBathrooms());
        entity.setNumberOfBedrooms(dto.getNumberOfBedrooms());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        return entity;
    }

    PropertyDto mapToDto(Property property) {
        PropertyDto dto=new PropertyDto();
        dto.setId(property.getId());
        dto.setName(property.getName());
        dto.setNumberOfGuests(property.getNumberOfGuests());
        dto.setNumberOfBeds(property.getNumberOfBeds());
        dto.setNumberOfBathrooms(property.getNumberOfBathrooms());
        dto.setNumberOfBedrooms(property.getNumberOfBedrooms());
        dto.setCity(property.getCity());
        dto.setCountry(property.getCountry());
        return dto;
    }
}
