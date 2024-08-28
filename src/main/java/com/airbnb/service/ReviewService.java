package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto addReview(ReviewDto reviewDto, AppUser user, long id);

    List<ReviewDto> listOfUserReviews(AppUser user);
}
