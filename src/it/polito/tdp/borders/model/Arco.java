package it.polito.tdp.borders.model;

public class Arco {
	
	private Country partenza;
	private Country arrivo;
	
	public Arco(Country partenza, Country arrivo) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
	}

	public Country getPartenza() {
		return partenza;
	}

	public void setPartenza(Country partenza) {
		this.partenza = partenza;
	}

	public Country getArrivo() {
		return arrivo;
	}

	public void setArrivo(Country arrivo) {
		this.arrivo = arrivo;
	}
	
	

}
