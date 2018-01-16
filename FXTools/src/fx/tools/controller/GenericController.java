package fx.tools.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class GenericController implements Initializable{
	
	private Stage stage;
	
	public void alert(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
		dialogoInfo.setTitle("Erro!");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void selectCity(Object obj) {
	}
	
	public void selectProfileToOrder(Object obj) {
	}
	
	public void updateTable(){
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
}
