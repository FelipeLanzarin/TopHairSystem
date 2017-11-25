package br.ths.controllers.main;

import java.net.URL;
import java.util.ResourceBundle;

import br.ths.screens.branch.employee.ScreeanEmployeeRelationManager;
import br.ths.screens.city.ScreeanCityRelationManager;
import br.ths.screens.profile.ScreeanProfileRelationManager;
import br.ths.tools.log.LogTools;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class ControllerMain implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO jogar todos os panes em um array para facilitar no moneto de preencher a agenda
		
	}
	
	
	public void openEmployeeManager(){
		try{
			ScreeanEmployeeRelationManager scream = new ScreeanEmployeeRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openCityManager(){
		try{
			ScreeanCityRelationManager scream = new ScreeanCityRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openProfileManager(){
		try{
			ScreeanProfileRelationManager scream = new ScreeanProfileRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
}
