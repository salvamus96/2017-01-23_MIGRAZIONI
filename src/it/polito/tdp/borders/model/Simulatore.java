package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	// Modello del mondo: la parte statica della simulazione
	// ogni stato all'inizio avrà 0 persone
	private Map <Country, Integer> stanziali;
	private Graph <Country, DefaultEdge> graph;
	
	//Parametri della simulazioni
	private int N_MIGRANTI = 1000;
	
	
	// Valori in output
	// stanziali
	private int T; // numero di passi della simulazione
	
	
	// Tipi di eventi / Coda di eventi
	private PriorityQueue <Evento> queue;
	
	
	public void init (Graph <Country, DefaultEdge> graph, Country partenza) {
		
		// inizializzazione tempo
		this.T = 1;
		
		// utile tener traccia del grafico per la simulazione
		this.graph = graph;
		
		// popolazione mappa
		this.stanziali = new HashMap<>();
		for (Country c : graph.vertexSet())
			stanziali.put(c, 0);
		
		// inizializzazione coda
		this.queue = new PriorityQueue<>();
		
		this.queue.add(new Evento (T, this.N_MIGRANTI, partenza));
		
	}
	
	/**
	 * La simulazione termina quando termino di processare gli eventi
	 */
	public void run () {
		
		Evento e;
		while ((e = this.queue.poll()) != null){
			
			this.T = e.getTempo();
			int arriviMigranti = e.getNumSpostamenti();
			Country partenza = e.getDestionation();
			
			List <Country> confinanti = Graphs.neighborListOf(this.graph, partenza);
			
			// Il 50% degli arrivi migranti si sposta in maniera uniforme nei vari stati confinanti
			
			// il grafo è fatto in modo da considerare solo vertici con almeno 1 stato confinante
			// perciò confinanti.size() non può mai essere pari a zero
			int numMigranti = (arriviMigranti /2 ) / confinanti.size(); // approssimano all'intero superiore
			
			
			if (numMigranti != 0)
				// genero eventi di migrazione sui confinanti
				for (Country arrivo : confinanti)
					this.queue.add(new Evento (this.T + 1, numMigranti, arrivo));
			
			
			int rimasti = arriviMigranti - numMigranti * confinanti.size(); 
			
			// persone totali dopo ogni passo della simulazione dopo ogni migrazione
			int numPersone = this.stanziali.get(partenza) + rimasti;
			this.stanziali.put(partenza, numPersone);
		}
		
	}

	public Map<Country, Integer> getStanziali() {
		return stanziali;
	}

	public int getT() {
		return T;
	}
	
	
}
