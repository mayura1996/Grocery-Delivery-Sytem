package com.demo.restaurant.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.demo.restaurant.service.entity.Restaurant;
import com.demo.restaurant.service.enums.AllowedDestinations;
import com.demo.restaurant.service.enums.Rating;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

	Page<Restaurant> findAllByRestaurantName(@Param("restaurantName") String name, Pageable pageable);

	Page<Restaurant> findAll(Pageable pageable);

	Page<Restaurant> findAllByRatingAndDestinationsIn(@Param("rating") Rating rating,
			@Param("destinations") List<AllowedDestinations> destinations, Pageable pageable);

	Page<Restaurant> findAllByDestinationsIn(@Param("destinations") List<AllowedDestinations> destination,
			Pageable pageable);

	Page<Restaurant> findAllByRating(@Param("rating") Rating rating, Pageable pageable);

	Page<Restaurant> findAllByRestaurantNameAndRatingAndDestinationsIn(@Param("restaurantName") String restaurantName,
			@Param("rating") Rating rating, @Param("destinations") List<AllowedDestinations> destinations,
			Pageable pageable);

	Page<Restaurant> findAllByRestaurantNameAndDestinationsIn(@Param("restaurantName") String restaurantName,
			@Param("destinations") List<AllowedDestinations> destinations, Pageable pageable);

	Page<Restaurant> findAllByRestaurantNameAndRating(@Param("restaurantName") String restaurantName,
			@Param("rating") Rating rating, Pageable pageable);

}
