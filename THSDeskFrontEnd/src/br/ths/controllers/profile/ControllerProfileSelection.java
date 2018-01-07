package br.ths.controllers.profile;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Profile;
import br.ths.beans.manager.ProfileManager;
import br.ths.screens.profile.ScreenProfileDetail;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerProfileSelection extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Profile> table;
	@FXML private TableColumn<Profile, String> columnOne;
	@FXML private TableColumn<Profile, String> columnTwo;
	@FXML private Label itemSelected;
	
	private List<Profile> list;
	private GenericController controller;
	private Profile profileSelected;
	private Stage stage;
	
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					Profile profile = table.getSelectionModel().getSelectedItem();
					if(profile != null){
						stage.close();
						controller.selectProfileToOrder(profile);
					}
				}else{
					Profile profile = table.getSelectionModel().getSelectedItem();
					if(profile != null){
						itemSelected.setText(profile.getName());
						profileSelected = profile;
					}
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
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
			itemSelected.setText("Nenhum Cliente Selecionado");
			profileSelected = null;
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<Profile> listProfiles){
		try {
			if(listProfiles== null){
				listProfiles = ProfileManager.getProfiles();
			}
			table.setItems(FXCollections.observableArrayList(listProfiles));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = ProfileManager.getProfiles();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
			updateTable(list);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	
	public void viewItem(){
		try {
			if(profileSelected != null){
				ScreenProfileDetail screenProfileDetail = new ScreenProfileDetail();
				screenProfileDetail.setOnlySee(true);
				screenProfileDetail.setProfile(profileSelected);
				screenProfileDetail.start(new Stage());
			}else{
				Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
				dialogoInfo.setTitle("Atenção!");
				dialogoInfo.setHeaderText("Selecione um Cliente para visulizálo!");
				dialogoInfo.showAndWait();
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public List<Profile> getList() {
		return list;
	}

	public void setList(List<Profile> list) {
		this.list = list;
	}

	public GenericController getController() {
		return controller;
	}

	public void setController(GenericController controller) {
		this.controller = controller;
	}

	public Profile getProfileSelected() {
		return profileSelected;
	}

	public void setProfileSelected(Profile profileSelected) {
		this.profileSelected = profileSelected;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
}
