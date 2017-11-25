package br.ths.controllers.city;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.City;
import br.ths.beans.manager.CityManager;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerCityRelation extends GenericController{
	
	private List<City> listCities;
	private GenericController lastController;
	private City citySelected;
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<City> table;
	@FXML private TableColumn<City, String> columnOne;
	@FXML private TableColumn<City, String> columnTwo;
	@FXML private TableColumn<City, String> columnThree;
	@FXML private Label itemSelected;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}	
	
	public void selectItem(){
		try {
			if(citySelected != null){
				lastController.selectCity(citySelected);
				this.getStage().close();
			}else{
				Alert dialog = new Alert(Alert.AlertType.WARNING);
				dialog.setTitle("Aviso!");
				dialog.setHeaderText("Selecione a cidade antes de prosseguir!");
				dialog.showAndWait();
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTableItem(){
		try {
			citySelected = table.getSelectionModel().getSelectedItem();
			if(citySelected != null){
				itemSelected.setText(citySelected.getName());
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterCities(){
		try {
			String cityName = textNameFilter.getText().toLowerCase();
			List<City> listCitiesSelects = new ArrayList<>();
			for (City city : listCities) {
				String name = city.getName().toLowerCase();
				if(name.contains(cityName)){
					listCitiesSelects.add(city);
				}
			}
			updateTable(listCitiesSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<City> listCities){
		try {
			if(listCities== null){
				listCities = this.listCities;
			}
			table.setItems(FXCollections.observableArrayList(listCities));
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void createTable(){
		try{
			listCities = CityManager.getCities();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("uf"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("country"));
			updateTable(listCities);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}


	public GenericController getLastController() {
		return lastController;
	}


	public void setLastController(GenericController lastController) {
		this.lastController = lastController;
	}
	
}
