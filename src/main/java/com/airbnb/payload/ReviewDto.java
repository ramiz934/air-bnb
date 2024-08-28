package com.airbnb.payload;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    private String rating;

    private String description;

    private Property property;

    private AppUser appUser;

}
