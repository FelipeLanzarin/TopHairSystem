package br.ths.screens.profile;

import java.net.URL;

import br.ths.beans.Profile;
import br.ths.controllers.profile.ControllerProfileDetail;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenProfileDetail extends Application {
	public Profile profile;
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PROFILE_DETAIL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Cliente");
			ControllerProfileDetail controllerProfileDetail = (ControllerProfileDetail) loader.getController();
			controllerProfileDetail.setProfile(profile);
			controllerProfileDetail.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
