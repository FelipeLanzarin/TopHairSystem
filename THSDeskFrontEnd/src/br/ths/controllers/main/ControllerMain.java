package br.ths.controllers.main;

import java.net.URL;
import java.util.ResourceBundle;

import br.ths.screens.branch.catalog.product.ScreenProductRelationManager;
import br.ths.screens.branch.employee.ScreenEmployeeRelationManager;
import br.ths.screens.catalog.category.ScreenCategoryRelationManager;
import br.ths.screens.catalog.subcategory.ScreenSubCategoryRelationManager;
import br.ths.screens.city.ScreenCityRelationManager;
import br.ths.screens.profile.ScreenProfileRelationManager;
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
			ScreenEmployeeRelationManager scream = new ScreenEmployeeRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openCityManager(){
		try{
			ScreenCityRelationManager scream = new ScreenCityRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openCategoryManager(){
		try{
			ScreenCategoryRelationManager scream = new ScreenCategoryRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openProfileManager(){
		try{
			ScreenProfileRelationManager scream = new ScreenProfileRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openSubCategoryManager(){
		try{
			ScreenSubCategoryRelationManager scream = new ScreenSubCategoryRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openProductManager(){
		try{
			ScreenProductRelationManager scream = new ScreenProductRelationManager();
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
}
