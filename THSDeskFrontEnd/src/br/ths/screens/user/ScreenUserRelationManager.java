package br.ths.screens.user;

import java.net.URL;

import br.ths.controllers.user.ControllerUserRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenUserRelationManager extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.USER_RELATION_MANAGER);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			ControllerUserRelationManager control = (ControllerUserRelationManager) loader.getController();
			control.setStage(stage);
			control.createTable();
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

}
