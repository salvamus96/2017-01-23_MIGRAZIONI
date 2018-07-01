package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

public class CountryIdMap {
	
	private Map <Integer, Country> map;
	
	public CountryIdMap () {
	
		this.map = new HashMap<>();
	}

	public Country getCountry (Country country) {
		
		Country old = this.map.get(country.getcCode());
		if (old == null)
			this.map.put(country.getcCode(), country);
		
		return map.get(country.getcCode());
	}

	public Country getCountry(int countryId) {
		return map.get(countryId);
	}
	
	
}
