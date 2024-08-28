package com.airbnb.repository;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.payload.ReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //One user can not post more than reviews on a single property

    @Query("select r from Review r where r.appUser=:user and r.property=:property")
    Review findByUserAndProperty(@Param("user") AppUser user,@Param("property") Property property);

    @Query("select r from Review r where r.appUser=:user")
    List<Review> findReviewsByUser(@Param("user") AppUser user);
}