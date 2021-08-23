package com.demo.restaurant.service.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.demo.restaurant.service.enums.AllowedDestinations;
import com.demo.restaurant.service.enums.Rating;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class Restaurant implements Serializable {

	private static final long serialVersionUID = -2101700283936373550L;

	// ID used to find the restaurant
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
	private Integer restaurantId;

	// Name of the restaurant
	@Column(name = "restaurant_name")
	private String restaurantName;

	// Restaurant
	@Column(name = "description")
	private String description;

	// Location information of the restaurant
	@Column(name = "address")
	private String address;

	// Menu of this restaurant
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MenuItem> menu;

	// destintions of this order
	@ElementCollection(targetClass = AllowedDestinations.class)
	@CollectionTable(name = "alowed_destinations", joinColumns = @JoinColumn(name = "destination_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "destinations")
	List<AllowedDestinations> destinations = new ArrayList<AllowedDestinations>(
			Arrays.asList(AllowedDestinations.values()));;

	// Status of this order
	@Enumerated(EnumType.STRING)
	@Column(name = "rating")
	Rating rating;

	@JsonCreator
	public Restaurant(@JsonProperty("restaurantName") String name, @JsonProperty("description") String description,
			@JsonProperty("address") String address, @JsonProperty("menu") List<MenuItem> menu,
			@JsonProperty("destinations") List<AllowedDestinations> destinations) {
		this.restaurantName = name;
		this.description = description;
		this.address = address;
		this.menu = menu;
		this.destinations = destinations;
		this.rating = Rating.Good;
	}

	public List<MenuItem> getMenu() {
		return this.menu;
	}

}
