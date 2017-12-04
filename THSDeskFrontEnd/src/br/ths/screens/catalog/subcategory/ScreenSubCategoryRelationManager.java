package br.ths.screens.catalog.subcategory;

import java.net.URL;

import br.ths.controllers.catalog.subcategory.ControllerSubCategoryRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenSubCategoryRelationManager extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.SUBCATEGORY_MANAGER);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			ControllerSubCategoryRelationManager control = (ControllerSubCategoryRelationManager) loader.getController();
			control.setStage(stage);
			control.createTable();
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
}
