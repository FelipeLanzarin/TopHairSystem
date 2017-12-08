package br.ths.controllers.user;

import java.net.URL;
import java.util.ResourceBundle;

import br.ths.beans.City;
import br.ths.beans.UserBranch;
import br.ths.beans.manager.UserBranchManager;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ControllerUserModal extends GenericController{
	
	@FXML private Label labelTitle;
	@FXML private TextField textLogin;
	@FXML private PasswordField textPassword;
	@FXML private ToggleGroup groupType;
	@FXML private RadioButton radioAdmin;
	@FXML private RadioButton radioUser;
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";
	
	public Boolean newUser;
	public UserBranch user;
	public ControllerUserRelationManager relation;
	public City city;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void actionLogin(){
		textLogin.setStyle("");
	}
	
	public void actionPassword(){
		textPassword.setStyle("");
	}
		
	public boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textLogin.getText().isEmpty()){
				textLogin.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textPassword.getText().isEmpty()){
				textPassword.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
	}
	
	//metodo chamado no botao salvar
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(user == null){
				user = new UserBranch();
			}
			
			user.setLogin(textLogin.getText());
			user.setPassword(textPassword.getText());
			RadioButton radioSelect = (RadioButton) groupType.getSelectedToggle();
			user.setType(radioSelect.getId());
			if(newUser){
				if(UserBranchManager.create(user)){
					getStage().close();
					relation.messageSucess("Usuário cadastrado com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar Usuário!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(UserBranchManager.update(user)){
					getStage().close();
					relation.messageSucess("Usuário alterado com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar Usuário!");
					dialogoInfo.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void populateTextFields(UserBranch user){
		try {
			if(user == null){
				return;
			}
			textLogin.setText(user.getLogin());
			if(user.getType() != null && "admin".equals(user.getType())){
				radioAdmin.setSelected(true);
			}else{
				radioUser.setSelected(true);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	
	public Boolean getNewUser() {
		return newUser;
	}

	public void setNewUser(Boolean newUser) {
		this.newUser = newUser;
	}

	public UserBranch getUser() {
		return user;
	}

	public void setUser(UserBranch user) {
		this.user = user;
		if(user != null){
			populateTextFields(user);
		}
	}

	public ControllerUserRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerUserRelationManager relation) {
		this.relation = relation;
	}
	
}
