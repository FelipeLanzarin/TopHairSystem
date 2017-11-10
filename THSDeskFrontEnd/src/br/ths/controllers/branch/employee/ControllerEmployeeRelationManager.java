package br.ths.controllers.branch.employee;

import java.util.List;

import br.ths.beans.Employee;
import br.ths.screens.branch.employee.ScreeanEmployeeModal;
import br.ths.tools.log.LogTools;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ControllerEmployeeRelationManager {
	
	public void clickButtonNew(){
		try{
			ScreeanEmployeeModal scream = new ScreeanEmployeeModal();
			scream.setNewEmployee(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByName(){
		
	}
	
	public void messageSucess(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setTitle("Sucesso");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
		updateTable(null);
	}
	
	public void updateTable(List<Employee> list){
		
	}
}
