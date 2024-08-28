package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.ReviewDto;
import com.airbnb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    //public ReviewController(ReviewService reviewService) {
      //  this.reviewService = reviewService;
    //}

    @PostMapping("/add")
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto,
                                                  @AuthenticationPrincipal AppUser user,
                                                  @RequestParam long id ) {
            ReviewDto dto = reviewService.addReview(reviewDto, user, id);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
        @GetMapping("/allReviews")
    public ResponseEntity<List<ReviewDto>> listReviewsOfUser(@AuthenticationPrincipal AppUser user){
        List<ReviewDto> reviewDto = reviewService.listOfUserReviews(user);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }


}
