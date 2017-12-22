package br.ths.screens.branch.catalog;

import java.net.URL;

import br.ths.beans.Category;
import br.ths.controllers.catalog.ControllerCatalogSubCategory;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCatalogSubCategory extends Application {
	
	private Category category;
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.CATALOG_SUB_CATEGORY);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			Scene scene = new Scene(fxmlParent);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			ControllerCatalogSubCategory control = (ControllerCatalogSubCategory) loader.getController();
			control.setStage(stage);
			control.setCategory(category);
			control.createTable();
			THSFrontUtils.setSceneCatalogSubCategory(scene);
			THSFrontUtils.setScreenCatalogSubCategory(this);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
