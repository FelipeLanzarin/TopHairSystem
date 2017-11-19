package br.ths.screens.city;

import java.io.IOException;
import java.net.URL;

import br.ths.controllers.city.ControllerCityRelation;
import br.ths.utils.XmlPathUtils;
import fx.tools.controller.GenericController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCityRelation extends Application {
	
	private GenericController lastController;
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.CITY_RELATION);
			Parent fxmlParent;
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			fxmlParent = (Parent) loader.load();
			stage.setResizable(false);
			stage.setScene(new Scene(fxmlParent));
			ControllerCityRelation control = (ControllerCityRelation) loader.getController();
			control.setStage(stage);
			control.setLastController(lastController);
			control.createTable();
			stage.setTitle("Cidades");
			stage.show();
		
		} catch (IOException e) {
			e.printStackTrace();
//			LogTools.logError(e);
		}
		
	}

	public GenericController getLastController() {
		return lastController;
	}

	public void setLastController(GenericController lastController) {
		this.lastController = lastController;
	}

}
