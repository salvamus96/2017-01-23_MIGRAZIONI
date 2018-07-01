package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO bdao; 
	private CountryIdMap map;
	
	private Graph <Country, DefaultEdge> graph;
	private List <Country> countries;
	
	private Simulatore simulatore;
	
	public Model () {
		
		this.bdao = new BordersDAO();
		this.map = new CountryIdMap();
	}

	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		// Caricamento vertici
		this.countries = this.bdao.getAllCountry(anno, map);
		
		Graphs.addAllVertices(this.graph, this.countries);
		
		// Caricamento archi
		List <Arco> archi = this.bdao.getAllEdges(anno, map);
		for (Arco a : archi)
			Graphs.addEdgeWithVertices(this.graph, a.getPartenza(), a.getArrivo());
		
		
		System.out.println(this.graph.vertexSet().size() + " " + this.graph.edgeSet().size());
	}

	/**
	 * Mi servo di una classe composta da Country e numConfini per restituire un risultato
	 * all'interfaccia grafica
	 * @return
	 */
	public List<CountryAndNumber> getResult() {
		
		List<CountryAndNumber> result = new ArrayList<>();
		for (Country c : this.countries)
			 
			// il numero di stati confinanti a uno stato è il GRADO del vertice
			result.add(new CountryAndNumber(c, this.graph.degreeOf(c)));
		
		return result;
	}

	public List <Country> getAllCountries() {
		return this.countries;
	}

	
	// 2° PUNTO
	
	
	public void simula(Country partenza) {
		this.simulatore = new Simulatore();
		
		this.simulatore.init(graph, partenza);
		this.simulatore.run();
	}

	public int getPassiSimulazione() {
		return this.simulatore.getT();
	}

	/**
	 * Riutilizzo la classe CountryAndNumber dove il number stavolta rappresenta il numero di
	 * persone che rimangono il quel country a fine simulazione
	 * @return
	 */
	public List<CountryAndNumber> getPersone() {
		List <CountryAndNumber> result = new ArrayList<>();
		Map <Country, Integer> stanziali = this.simulatore.getStanziali();
		
		// Conversione la mappa in una lista 
			
		for (Country c : stanziali.keySet())
			result.add(new CountryAndNumber(c, stanziali.get(c)));
		
		// ordinamento degli elementi in ordine decrescenti di Number
		// non era necessario fare un comparatore perchè la classe CountryAndNumber aveva
		// già il suo comparatore che poteva essere adattato qui
		// Si è fatto per esempio: un comparatore all'interno del sort senza creare una
		// classe comparatore
		Collections.sort(result, new Comparator <CountryAndNumber>() {

			@Override
			public int compare(CountryAndNumber c1, CountryAndNumber c2) {
				return -(c1.getNumConfini() - c2.getNumConfini());
			}
			
		});
		
		return result;
	}
	
	
}
