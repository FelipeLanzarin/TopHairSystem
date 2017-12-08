package br.ths.screens.user;

import java.net.URL;

import br.ths.beans.UserBranch;
import br.ths.controllers.user.ControllerUserModal;
import br.ths.controllers.user.ControllerUserRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenUserModal extends Application {
	public Boolean newUser;
	public ControllerUserRelationManager relation;
	public UserBranch user;
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.USER_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Usuários");
			ControllerUserModal controllerUserModal = (ControllerUserModal) loader.getController();
			controllerUserModal.setNewUser(newUser);
			controllerUserModal.setRelation(relation);
			controllerUserModal.setUser(user);
			controllerUserModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
	public Boolean getNewUser() {
		return newUser;
	}
	public void setNewUser(Boolean newUser) {
		this.newUser = newUser;
	}
	public ControllerUserRelationManager getRelation() {
		return relation;
	}
	public void setRelation(ControllerUserRelationManager relation) {
		this.relation = relation;
	}
	public UserBranch getUser() {
		return user;
	}
	public void setUser(UserBranch user) {
		this.user = user;
	}
	
}
