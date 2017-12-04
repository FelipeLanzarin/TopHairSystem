package br.ths.controllers.profile;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Profile;
import br.ths.beans.manager.ProfileManager;
import br.ths.screens.profile.ScreenProfileModal;
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

public class ControllerProfileRelationManager extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Profile> table;
	@FXML private TableColumn<Profile, String> columnOne;
	@FXML private TableColumn<Profile, String> columnTwo;
	@FXML private TableColumn<Profile, String> columnThree;
	@FXML private TableColumn<Profile, String> columnFour;
	@FXML private TableColumn<Profile, String> columnFive;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	
	private List<Profile> list;
	
	public void clickButtonNew(){
		try{
			ScreenProfileModal scream = new ScreenProfileModal();
			scream.setNewProfile(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			Profile profile = table.getSelectionModel().getSelectedItem();
			if(profile == null){
				return;
			}
			ScreenProfileModal scream = new ScreenProfileModal();
			scream.setNewProfile(false);
			scream.setRelation(this);
			scream.setProfile(profile);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try {
			Profile profile = table.getSelectionModel().getSelectedItem();
			if(profile == null){
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir o cliente "+ profile.getName()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				if(ProfileManager.delete(profile)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Cliente excluído com sucesso");
					dialog.showAndWait();
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro ao excluir Cliente!");
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			Profile profile = table.getSelectionModel().getSelectedItem();
			if(profile != null){
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
			String profileName = textNameFilter.getText().toLowerCase();
			List<Profile> listProfilesSelects = new ArrayList<>();
			for (Profile profile : list) {
				String name = profile.getName().toLowerCase();
				if(name.contains(profileName)){
					listProfilesSelects.add(profile);
				}
			}
			updateTable(listProfilesSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<Profile> listProfiles){
		try {
			if(listProfiles== null){
				listProfiles = ProfileManager.getProfiles();
			}
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listProfiles));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = ProfileManager.getProfiles();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("email"));
			columnFour.setCellValueFactory(new PropertyValueFactory<>("telephone"));
			columnFive.setCellValueFactory(new PropertyValueFactory<>("address"));
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

	public List<Profile> getList() {
		return list;
	}

	public void setList(List<Profile> list) {
		this.list = list;
	}

}
