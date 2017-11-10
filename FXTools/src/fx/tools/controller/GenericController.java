package fx.tools.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class GenericController implements Initializable{
	
	public void alert(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
		dialogoInfo.setTitle("Erro!");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}
