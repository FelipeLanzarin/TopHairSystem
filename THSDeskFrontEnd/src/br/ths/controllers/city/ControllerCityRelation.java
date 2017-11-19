package br.ths.controllers.city;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.City;
import br.ths.utils.TableViewUtils;
import br.ths.utils.beans.CityRown;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerCityRelation extends GenericController{
	
	private List<CityRown> listCities;
	private GenericController lastController;
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<CityRown> table;
	@FXML private TableColumn<City, String> columnOne;
	@FXML private TableColumn<City, String> columnTwo;
	@FXML private TableColumn<City, String> columnThree;
	@FXML private TableColumn<City, String> columnFour;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}	
	
	
	public void filterCities(){
		String cityName = textNameFilter.getText().toLowerCase();
		List<CityRown> listCitiesSelects = new ArrayList<>();
		for (CityRown city : listCities) {
			String name = city.getName().toLowerCase();
			if(name.contains(cityName)){
				listCitiesSelects.add(city);
			}
		}
		updateTable(listCitiesSelects);
	}
	
	public void updateTable(List<CityRown> listCities){
		if(listCities== null){
			listCities = this.listCities;
		}
		table.setItems(FXCollections.observableArrayList(listCities));

	}
	public void createTable(){
		listCities = TableViewUtils.getCities(getStage().getScene(), lastController, this);
		columnOne.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnTwo.setCellValueFactory(new PropertyValueFactory<>("uf"));
		columnThree.setCellValueFactory(new PropertyValueFactory<>("country"));
		columnFour.setCellValueFactory(new PropertyValueFactory<>("select"));
		updateTable(listCities);
	}


	public GenericController getLastController() {
		return lastController;
	}


	public void setLastController(GenericController lastController) {
		this.lastController = lastController;
	}
	
}
