/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNumber;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
    		
    		try {
    			
    			int anno = Integer.parseInt(this.txtAnno.getText());
    			
    			model.creaGrafo(anno);
    			
    			List<CountryAndNumber> result = this.model.getResult();
    			
    			if (result.size() > 0) {	
	    			Collections.sort(result);
	    			
	    			this.txtResult.appendText("Stati nel " + anno + ":\n\n");
	    			for (CountryAndNumber c : result)
	    				this.txtResult.appendText(c + "\n");
    			}
    			else
    				this.txtResult.appendText("Non ci sono stati corrispondenti!\n");
    			
    			this.boxNazione.getItems().clear();
    			this.boxNazione.getItems().addAll(model.getAllCountries());
    			
    		}catch (NumberFormatException e) {
    			this.txtResult.appendText("Inseriere un anno presente nel DB!\n");
    		}
    		
    	}catch(RuntimeException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ERRORE: connessione DB\n!");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Country partenza = this.boxNazione.getValue();
    	if (partenza == null) {
    		this.txtResult.appendText("Selezionare una nazione!\n");
    		return;
    	}
    	
    	model.simula(partenza);
    	int passi = model.getPassiSimulazione();
    	
    	// Riutilizzo della classe CountryAndNumber
    	List <CountryAndNumber> result = model.getPersone();

    	this.txtResult.appendText("Simulazione dallo stato : " + partenza + "\n");
    	this.txtResult.appendText("Durata : " + passi + "\n");
    	
    	for (CountryAndNumber c : result)
    		if (c.getNumConfini() != 0)
    		
    			// il getNumConfini stavolta sta ad indicare le persone restanti nello stato a fine simulazione
    			this.txtResult.appendText(c.getCountry() + " - " + c.getNumConfini() + "\n");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
