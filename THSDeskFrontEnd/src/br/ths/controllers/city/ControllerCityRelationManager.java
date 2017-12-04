package br.ths.controllers.city;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.City;
import br.ths.beans.manager.CityManager;
import br.ths.screens.city.ScreenCityModal;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerCityRelationManager extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<City> table;
	@FXML private TableColumn<City, String> columnOne;
	@FXML private TableColumn<City, String> columnTwo;
	@FXML private TableColumn<City, String> columnThree;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	
	private List<City> list;
	
	public void clickButtonNew(){
		try{
			ScreenCityModal scream = new ScreenCityModal();
			scream.setNewCity(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			City city = table.getSelectionModel().getSelectedItem();
			if(city == null){
				return;
			}
			ScreenCityModal scream = new ScreenCityModal();
			scream.setNewCity(false);
			scream.setRelation(this);
			scream.setCity(city);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try {
			City city = table.getSelectionModel().getSelectedItem();
			if(city == null){
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir a Cidade "+ city.getName()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				if(CityManager.delete(city)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Cidade excluída com sucesso");
					dialog.showAndWait();
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Cidade possui uma pessoa!");
					dialog.setContentText("Essa Cidade já possui uma pessoa. Por esse motivo você não pode excluir a mesma.");
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			City city = table.getSelectionModel().getSelectedItem();
			if(city != null){
				disableButtons(false);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void disableButtons(Boolean disable){
		buttonEdit.setDisable(disable);
		buttonDelete.setDisable(disable);
	}
	
	
	public void filterByName(){
		try {
			String cityName = textNameFilter.getText().toLowerCase();
			List<City> listCitysSelects = new ArrayList<>();
			for (City city : list) {
				String name = city.getName().toLowerCase();
				if(name.contains(cityName)){
					listCitysSelects.add(city);
				}
			}
			updateTable(listCitysSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<City> listCitys){
		try {
			if(listCitys== null){
				listCitys = CityManager.getCities();
			}
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listCitys));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = CityManager.getCities();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("uf"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("country"));
			updateTable(list);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void messageSucess(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setTitle("Sucesso");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
		updateTable(null);
	}

	public List<City> getList() {
		return list;
	}

	public void setList(List<City> list) {
		this.list = list;
	}

}
