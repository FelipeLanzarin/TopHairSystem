package br.ths.screens.branch.catalog;

import java.net.URL;

import br.ths.controllers.catalog.ControllerCatalogCategory;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCatalogCategory extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.CATALOG_CATEGORY);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			Scene scene = new Scene(fxmlParent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			ControllerCatalogCategory control = (ControllerCatalogCategory) loader.getController();
			control.setStage(stage);
			control.createTable();
			THSFrontUtils.setSceneCatalogCategory(scene);
			THSFrontUtils.setScreenCatalogCategory(this);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
}
