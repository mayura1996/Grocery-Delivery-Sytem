package com.demo.restaurant.service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.restaurant.service.entity.Restaurant;
import com.demo.restaurant.service.enums.AllowedDestinations;
import com.demo.restaurant.service.enums.Rating;

public interface RestaurantService {

	Page<Restaurant> findAll(Pageable pageable);

	Restaurant findById(Integer id);

	List<Restaurant> saveRestaurants(List<Restaurant> restaurants);

	void deleteById(Integer id);

	void deleteAll();

	Page<Restaurant> findAllByDestinationsIn(List<AllowedDestinations> destination, Pageable pageable);

	Page<Restaurant> findAllByRatingAndDestinationsIn(Rating rating, List<AllowedDestinations> destinations,
			Pageable pageable);

	Page<Restaurant> findAllByRating(Rating rating, Pageable pageable);

	Page<Restaurant> findAllByRestaurantNameAndRatingAndDestinationsIn(String restaurantName, Rating rating,
			List<AllowedDestinations> destinations, Pageable pageable);

	Page<Restaurant> findAllByRestaurantNameAndDestinationsIn(String restaurantName,
			List<AllowedDestinations> destinations, Pageable pageable);

	Page<Restaurant> findAllByRestaurantNameAndRating(String shopName, Rating rating, Pageable pageable);

	Page<Restaurant> findAllByRestaurantName(String restaurantName, Pageable pageable);
}
