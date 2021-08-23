package com.demo.restaurant.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.restaurant.service.entity.Restaurant;
import com.demo.restaurant.service.enums.AllowedDestinations;
import com.demo.restaurant.service.enums.Rating;
import com.demo.restaurant.service.service.RestaurantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "Restaurant", description = "Available Restaurants")
public class RestaurantRestController {
	private final String defaultPageSize = "10";
	private final String defaultPageNum = "0";

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping("/restaurants")
	@ApiOperation(value = "View All available list of Restaurants based on criteria")
	Page<Restaurant> findAll(@RequestParam(value = "restaurantName", required = false) String restaurantName,
			@RequestParam(value = "rating", required = false) Rating rating,
			@RequestParam(value = "destinations", required = false) List<AllowedDestinations> destinations,
			@RequestParam(value = "page", required = false, defaultValue = defaultPageNum) int page,
			@RequestParam(value = "size", required = false, defaultValue = defaultPageSize) Integer size) {
		return filterRestaurntAsCriteria(restaurantName, rating, destinations, page, size);
	}

	private Page<Restaurant> filterRestaurntAsCriteria(String restaurantName, Rating rating,
			List<AllowedDestinations> destinations, int page, Integer size) {
		if (null != rating && null != destinations) {
			return restaurantService.findAllByRatingAndDestinationsIn(rating, destinations, PageRequest.of(page, size));
		} else if (null == rating && null != destinations) {
			return restaurantService.findAllByDestinationsIn(destinations, PageRequest.of(page, size));
		} else if (null != rating && null == destinations) {
			return restaurantService.findAllByRating(rating, PageRequest.of(page, size));
		} else if (null != restaurantName && null == rating && null != destinations) {
			return restaurantService.findAllByRestaurantNameAndDestinationsIn(restaurantName, destinations,
					PageRequest.of(page, size));
		} else if (null == restaurantName && null != rating && null == destinations) {
			return restaurantService.findAllByRestaurantNameAndRating(restaurantName, rating,
					PageRequest.of(page, size));
		} else if (null != restaurantName && null == rating && null == destinations) {
			return restaurantService.findAllByRestaurantName(restaurantName,
					PageRequest.of(page, size));
		} else {
			return restaurantService.findAll(PageRequest.of(page, size));
		}
	}

	@GetMapping("/restaurants/{id}")
	@ApiIgnore
	Restaurant findById(@PathVariable(value = "id") Integer id) {
		return restaurantService.findById(id);
	}

	@PostMapping("/restaurants")
	@ApiOperation(value = "Add Single/Multiple Restaurants")
	List<Restaurant> save(@RequestBody List<Restaurant> restaurants) {
		return restaurantService.saveRestaurants(restaurants);
	}

	@DeleteMapping("/restaurants")
	@ApiIgnore
	void deleteAll() {
		restaurantService.deleteAll();
	}

	@DeleteMapping("/restaurants/{id}")
	@ApiIgnore
	void deleteById(@PathVariable(value = "id") Integer id) {
		restaurantService.deleteById(id);
	}
}
