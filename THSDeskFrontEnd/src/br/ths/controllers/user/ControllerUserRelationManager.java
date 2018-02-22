package br.ths.controllers.user;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.UserBranch;
import br.ths.beans.manager.UserBranchManager;
import br.ths.screens.user.ScreenUserModal;
import br.ths.tools.THSTools;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerUserRelationManager extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<UserBranch> table;
	@FXML private TableColumn<UserBranch, String> columnOne;
	@FXML private TableColumn<UserBranch, String> columnTwo;

	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	
	private List<UserBranch> list;
	
	public void clickButtonNew(){
		try{
			ScreenUserModal scream = new ScreenUserModal();
			scream.setNewUser(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			UserBranch user = table.getSelectionModel().getSelectedItem();
			if(user == null){
				return;
			}
			if(user.getLogin().equals("admin")){
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setTitle("Erro!");
				dialog.setHeaderText("Você não tem permissão para editar o usuário admin!");
				dialog.showAndWait();
				return;
			}
			ScreenUserModal scream = new ScreenUserModal();
			scream.setNewUser(false);
			scream.setRelation(this);
			scream.setUser(user);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try {
			UserBranch user = table.getSelectionModel().getSelectedItem();
			if(user == null){
				return;
			}
			if(user.getLogin().equals("admin")){
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setTitle("Erro!");
				dialog.setHeaderText("Você não tem permissão para excluir o usuário admin!");
				dialog.showAndWait();
				return;
			}
			if(user.getLogin().equals(THSTools.getUserBranchSession().getLogin())){
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setTitle("Erro!");
				dialog.setHeaderText("Você não pode excluir o usuário que está logado nessa sessão!");
				dialog.showAndWait();
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir o usuário "+ user.getLogin()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				if(UserBranchManager.delete(user)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Usuário excluído com sucesso");
					dialog.showAndWait();
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro ao excluir Usuário!");
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			UserBranch user = table.getSelectionModel().getSelectedItem();
			if(user != null){
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
			String userName = textNameFilter.getText().toLowerCase();
			List<UserBranch> listUsersSelects = new ArrayList<>();
			for (UserBranch user : list) {
				String name = user.getLogin().toLowerCase();
				if(name.contains(userName)){
					listUsersSelects.add(user);
				}
			}
			updateTable(listUsersSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<UserBranch> listUsers){
		try {
			if(listUsers== null){
				listUsers = UserBranchManager.getUserBranchs();
			}
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listUsers));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = UserBranchManager.getUserBranchs();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("login"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("type"));
			columnTwo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserBranch,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<UserBranch, String> user) {
					String type = "Usuário";
					if("admin".equals(user.getValue().getType())){
						type = "Admin";
					}
					return new SimpleStringProperty(type);
				}
			});
			
			
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

	public List<UserBranch> getList() {
		return list;
	}

	public void setList(List<UserBranch> list) {
		this.list = list;
	}

}
