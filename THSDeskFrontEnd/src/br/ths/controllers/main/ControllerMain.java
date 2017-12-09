package br.ths.controllers.main;

import java.net.URL;
import java.util.ResourceBundle;

import br.ths.screens.branch.catalog.product.ScreenProductRelationManager;
import br.ths.screens.branch.employee.ScreenEmployeeRelationManager;
import br.ths.screens.catalog.category.ScreenCategoryRelationManager;
import br.ths.screens.catalog.subcategory.ScreenSubCategoryRelationManager;
import br.ths.screens.city.ScreenCityRelationManager;
import br.ths.screens.profile.ScreenProfileRelation;
import br.ths.screens.profile.ScreenProfileRelationManager;
import br.ths.screens.user.ScreenUserRelationManager;
import br.ths.tools.log.LogTools;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class ControllerMain implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO jogar todos os panes em um array para facilitar no momento de preencher a agenda
		
	}
	
	public void openEmployeeManager(){
		try{
			ScreenEmployeeRelationManager screen = new ScreenEmployeeRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openCityManager(){
		try{
			ScreenCityRelationManager screen = new ScreenCityRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openCategoryManager(){
		try{
			ScreenCategoryRelationManager screen = new ScreenCategoryRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openProfileManager(){
		try{
			ScreenProfileRelationManager screen = new ScreenProfileRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openSubCategoryManager(){
		try{
			ScreenSubCategoryRelationManager screen = new ScreenSubCategoryRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openProductManager(){
		try{
			ScreenProductRelationManager screen = new ScreenProductRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openUserRelationManager(){
		try{
			ScreenUserRelationManager screen = new ScreenUserRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
		
	public void openProfileRelation(){
		try{
			ScreenProfileRelation screen = new ScreenProfileRelation();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
}
