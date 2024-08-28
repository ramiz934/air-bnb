//package com.airbnb.service;
//
//import com.airbnb.entity.AppUser;
//import com.airbnb.entity.Property;
//import com.airbnb.entity.Review;
//import com.airbnb.exception.UserExists;
//import com.airbnb.payload.ReviewDto;
//import com.airbnb.repository.AppUserRepository;
//import com.airbnb.repository.PropertyRepository;
//import com.airbnb.repository.ReviewRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ReviewServiceImpl implements ReviewService{
//
//    private ReviewRepository reviewRepository;
//    private PropertyService propertyService;
//    private AppUserRepository appUserRepository;
//    private final PropertyRepository propertyRepository;
//
//    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyService propertyService, AppUserRepository appUserRepository,
//                             PropertyRepository propertyRepository) {
//        this.reviewRepository = reviewRepository;
//        this.propertyService = propertyService;
//        this.appUserRepository = appUserRepository;
//        this.propertyRepository = propertyRepository;
//    }
//
//    @Override
//    public ReviewDto addReview(ReviewDto reviewDto, AppUser user, long id) {
//    Property property = propertyRepository.findById(id)
//            .orElseThrow(() -> new UserExists("Property not found"));
//        ReviewDto reviewDetails = reviewRepository.findByUserAndProperty(user, property);
//        if(reviewDetails != null){
//            throw new ("Review already exists for this property and user");
//        }
//        Review review = mapTOEntity(reviewDto);
//        review.setProperty(property);
//        review.setAppUser(user);
//        Review saved = reviewRepository.save(review);
//        return mapToDto(saved);
//    }
//
//    ReviewDto mapToDto(Review review){
//        ReviewDto reviewDto = new ReviewDto();
//        reviewDto.setId(review.getId());
//        reviewDto.setRating(review.getRating());
//        reviewDto.setDescription(review.getDescription());
//        reviewDto.setProperty(review.getProperty());
//        reviewDto.setAppUser(review.getAppUser());
//        return reviewDto;
//    }
//    Review mapTOEntity(ReviewDto reviewDto){
//        Review review=new Review();
//        review.setId(reviewDto.getId());
//        review.setRating(reviewDto.getRating());
//        review.setDescription(reviewDto.getDescription());
//        review.setProperty(reviewDto.getProperty());
//        review.setAppUser(reviewDto.getAppUser());
//        return review;
//    }
//}
package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.exception.PropertyNotFoundException;
import com.airbnb.exception.ReviewAlreadyExistsException;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.ReviewDto;
import com.airbnb.repository.AppUserRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private  ReviewRepository reviewRepository;
    @Autowired
    private  PropertyService propertyService;
    @Autowired
    private  AppUserRepository appUserRepository;
    @Autowired
    private  PropertyRepository propertyRepository;
    @Autowired
    private AppUserServiceImpl appUserService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyService propertyService,
                             AppUserRepository appUserRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyService = propertyService;
        this.appUserRepository = appUserRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public ReviewDto addReview(ReviewDto reviewDto, AppUser user, long id)  {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        Review existingReview = reviewRepository.findByUserAndProperty(user, property);
        if (existingReview != null) {
            throw new ReviewAlreadyExistsException("Review already exists for this property and user");
        }
        Review review = mapToEntity(reviewDto);
        review.setProperty(property);
        review.setAppUser(user);
        Review savedReview = reviewRepository.save(review);
        return mapToDto(savedReview);
    }

    @Override
    public List<ReviewDto> listOfUserReviews(AppUser user) {
       // AppUser user = appUserService.mapToEntity(userDto);
        List<Review> byUserReviews = reviewRepository.findReviewsByUser(user);
        List<ReviewDto> dtos = byUserReviews.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
        return dtos;
    }

    // Convert Review entity to ReviewDto
    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setDescription(review.getDescription());
        // Assuming PropertyDto and UserDto exist, map the related entities to DTOs
        reviewDto.setProperty(review.getProperty());
        reviewDto.setAppUser(review.getAppUser());
        return reviewDto;
    }

    // Convert ReviewDto to Review entity
    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        // Property and AppUser are set outside of this method
        return review;
    }
}

