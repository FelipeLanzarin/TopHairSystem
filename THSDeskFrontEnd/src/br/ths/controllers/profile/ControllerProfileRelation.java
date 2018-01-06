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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerProfileRelation extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Profile> table;
	@FXML private TableColumn<Profile, String> columnOne;
	@FXML private TableColumn<Profile, String> columnTwo;
	@FXML private TableColumn<Profile, String> columnThree;
	@FXML private TableColumn<Profile, String> columnFour;
	@FXML private TableColumn<Profile, String> columnFive;
	
	private List<Profile> list;
	
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					Profile profile = table.getSelectionModel().getSelectedItem();
					if(profile != null){
						ScreenProfileDetail screen = new ScreenProfileDetail();
						screen.setProfile(profile);
						screen.start(new Stage());
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
