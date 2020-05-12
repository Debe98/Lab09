
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="cmbStati"
    private ComboBox<Country> cmbStati; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	String rawAnno = txtAnno.getText();
    	if (rawAnno.equals("")) {
    		txtResult.setText("Inserisci un anno!");
    		return;
    	}
    	int anno;
    	try {
    		anno =Integer.parseInt(rawAnno);
    	} catch (Exception e) {
			txtResult.setText("Il valore inserito non è un anno");
			return;
		}
    	try {
    		Graph<Country, DefaultEdge> grafo = model.getConfini(anno);
			txtResult.setText("Vertici: "+grafo.vertexSet().size());
			txtResult.appendText("\nConfini: "+grafo.edgeSet().size());
			txtResult.appendText("\nGrafo: "+grafo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			txtResult.setText(e.getMessage());
		}
    }
    
    @FXML
    void doCalcolaVicini(ActionEvent event) {
    	Country c = cmbStati.getValue();
    	List <Country> vicini;
    	try {
			vicini = model.getVicini(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			txtResult.setText(e.getMessage());
			return;
		}
    	if (vicini.size() == 1) {
    		txtResult.setText("Nessuno stato raggiungibile da "+c+" nell'anno "+model.getAnno()+".");
    		return;
    	}
    	txtResult.setText("Trovati "+(vicini.size()-1)+" stati raggiungibili da "+c+" nell'anno "+model.getAnno()+":\n");
    	for (int i = 1; i < vicini.size(); i++) {
    		txtResult.appendText(vicini.get(i)+"\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbStati.getItems().addAll(model.getPaesi());
    	try {
    		cmbStati.setValue(cmbStati.getItems().get(0));
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
