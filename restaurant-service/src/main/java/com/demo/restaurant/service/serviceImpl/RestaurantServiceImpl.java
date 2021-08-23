package com.demo.restaurant.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.restaurant.service.entity.Restaurant;
import com.demo.restaurant.service.enums.AllowedDestinations;
import com.demo.restaurant.service.enums.Rating;
import com.demo.restaurant.service.repository.RestaurantRepository;
import com.demo.restaurant.service.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private RestaurantRepository repository;

	@Autowired
	public RestaurantServiceImpl(RestaurantRepository repository) {
		this.repository = repository;
	}

	@Override
	public Page<Restaurant> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	@Override
	public Restaurant findById(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public List<Restaurant> saveRestaurants(List<Restaurant> restaurantList) {
		for (Restaurant each : restaurantList) {
			each.setDestinations(new ArrayList<AllowedDestinations>(Arrays.asList(AllowedDestinations.values())));
			if (each.getMenu().size() > 0) {
				each.getMenu().stream().forEach(menuItem -> {
					menuItem.setRestaurant(each);
				});
			}
		}
		return repository.saveAll(restaurantList);
	}

	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Page<Restaurant> findAllByRatingAndDestinationsIn(Rating rating, List<AllowedDestinations> destinations,
			Pageable pageable) {
		return repository.findAllByRatingAndDestinationsIn(rating, destinations, pageable);
	}

	@Override
	public Page<Restaurant> findAllByDestinationsIn(List<AllowedDestinations> destination, Pageable pageable) {
		return repository.findAllByDestinationsIn(destination, pageable);
	}

	@Override
	public Page<Restaurant> findAllByRating(Rating rating, Pageable pageable) {
		return repository.findAllByRating(rating, pageable);
	}

	@Override
	public Page<Restaurant> findAllByRestaurantNameAndRatingAndDestinationsIn(String restaurantName, Rating rating,
			List<AllowedDestinations> destinations, Pageable pageable) {
		return repository.findAllByRestaurantNameAndRatingAndDestinationsIn(restaurantName, rating, destinations,
				pageable);
	}

	@Override
	public Page<Restaurant> findAllByRestaurantNameAndDestinationsIn(String restaurantName,
			List<AllowedDestinations> destinations, Pageable pageable) {
		return repository.findAllByRestaurantNameAndDestinationsIn(restaurantName, destinations, pageable);
	}

	@Override
	public Page<Restaurant> findAllByRestaurantNameAndRating(String restaurantName, Rating rating, Pageable pageable) {
		return repository.findAllByRestaurantNameAndRating(restaurantName, rating, pageable);
	}

	@Override
	public Page<Restaurant> findAllByRestaurantName(String restaurantName, Pageable pageable) {
		return repository.findAllByRestaurantName(restaurantName, pageable);
	}

}
