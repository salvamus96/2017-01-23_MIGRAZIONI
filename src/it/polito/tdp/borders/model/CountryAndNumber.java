package it.polito.tdp.borders.model;

public class CountryAndNumber implements Comparable <CountryAndNumber>{

	private Country country;
	private int numConfini;
	
	public CountryAndNumber(Country country, int numConfini) {
		super();
		this.country = country;
		this.numConfini = numConfini;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getNumConfini() {
		return numConfini;
	}

	public void setNumConfini(int numConfini) {
		this.numConfini = numConfini;
	}

	@Override
	public int compareTo(CountryAndNumber o) {
		return -(this.numConfini - o.numConfini);
	}

	@Override
	public String toString() {
		return String.format("%s (%s) - %d", this.country.getStateAbb(), this.country.getStateName(), this.numConfini);
	}
	
	
	
}
