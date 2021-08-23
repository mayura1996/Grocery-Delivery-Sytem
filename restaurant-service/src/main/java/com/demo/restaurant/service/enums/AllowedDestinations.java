package com.demo.restaurant.service.enums;
public enum AllowedDestinations {
		// Constants with values
		Colombo(560032), Negombo(560033), Kadawatha(560034), Nugegoda(560035), Rathmalana(560036);

		private int destinations;

		// Constructor to initialize the instance variable
		AllowedDestinations(int destinations) {
			this.setDestinations(destinations);
		}

		public int getDestinations() {
			return destinations;
		}

		public void setDestinations(int destinations) {
			this.destinations = destinations;
		}
	}