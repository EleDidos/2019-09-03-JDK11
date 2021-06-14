/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Portion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    private String partenza;

    @FXML
    void doCammino(ActionEvent event) {
    	if(model.getGraph()==null) {
    		txtResult.setText("Devi prima creare il GRAFO");
    		return;
    	}
    	
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...\n");
    	
    	Integer N;//passi
    	try {
    		N=Integer.parseInt(txtCalorie.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Inserire un numero intero di passi");
    		return;
    	}catch(NullPointerException npe) {
    		txtResult.setText("Inserire un numero intero di passi");
    		return;
    	}
    	
    	txtResult.appendText("Il cammino con peso MAX ha peso: "+model.getPeso()+"\n");
    	for(String p: model.trovaPercorso(partenza, N))
    		txtResult.appendText(p+"\n");
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	if(model.getGraph()==null) {
    		txtResult.setText("Devi prima creare il GRAFO");
    		return;
    	}
    	
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	
    	try {
    		partenza=boxPorzioni.getValue();
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegliere una tipologia di porzione");
    		return;
    	}
    	txtResult.appendText("\nI tipi di porzione direttamente connessi con quello selezionato sono:\n");
    	txtResult.appendText(model.getVicini(partenza));
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	
    	Integer calorie;
    	try {
    		calorie=Integer.parseInt(txtCalorie.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Inserire un numero intero");
    		return;
    	}catch(NullPointerException npe) {
    		txtResult.setText("Inserire un numero intero");
    		return;
    	}
    	
    	this.model.creaGrafo(calorie);
    	
    	txtResult.appendText("Caratteristiche del GRAFO:\n"+"#VERTICI = "+model.getNVertici()+"\n#ARCHI = "+model.getNArchi());
    	
    	List <String> ordinate=this.model.getVertici();
    	
    	Collections.sort(ordinate);
    	boxPorzioni.getItems().addAll(ordinate);
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    }
}
