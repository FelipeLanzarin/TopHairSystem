package br.ths.screens.profile;

import java.net.URL;

import br.ths.beans.Profile;
import br.ths.controllers.profile.ControllerProfileModal;
import br.ths.controllers.profile.ControllerProfileRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreeanProfileModal extends Application {
	public Boolean newProfile;
	public ControllerProfileRelationManager relation;
	public Profile profile;
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PROFILE_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Clientes");
			ControllerProfileModal controllerProfileModal = (ControllerProfileModal) loader.getController();
			controllerProfileModal.setNewProfile(newProfile);
			controllerProfileModal.setRelation(relation);
			controllerProfileModal.setProfile(profile);
			controllerProfileModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
	public Boolean getNewProfile() {
		return newProfile;
	}
	public void setNewProfile(Boolean newProfile) {
		this.newProfile = newProfile;
	}
	public ControllerProfileRelationManager getRelation() {
		return relation;
	}
	public void setRelation(ControllerProfileRelationManager relation) {
		this.relation = relation;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
