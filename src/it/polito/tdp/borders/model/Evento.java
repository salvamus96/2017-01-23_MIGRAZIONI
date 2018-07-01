package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento> {

	private int tempo;
	private int numSpostamenti;
	private Country destionation;
	
	public Evento(int tempo, int numSpostamenti, Country destionation) {
		super();
		this.tempo = tempo;
		this.numSpostamenti = numSpostamenti;
		this.destionation = destionation;
	}

	public int getTempo() {
		return tempo;
	}

	public int getNumSpostamenti() {
		return numSpostamenti;
	}

	public Country getDestionation() {
		return destionation;
	}

	
	
	@Override
	public String toString() {
		return String.format("%d ) %s - %d", this.tempo, this.destionation, this.numSpostamenti) ;
	}

	@Override
	public int compareTo(Evento other) {
		// ORDINAMENTO CRESCENTE SU TEMPO
		return this.tempo - other.tempo;
	}

}
