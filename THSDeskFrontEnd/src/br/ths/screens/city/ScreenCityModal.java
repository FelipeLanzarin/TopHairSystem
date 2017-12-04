package br.ths.screens.city;

import java.net.URL;

import br.ths.beans.City;
import br.ths.controllers.city.ControllerCityModal;
import br.ths.controllers.city.ControllerCityRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCityModal extends Application {
	
	public Boolean newCity;
	public ControllerCityRelationManager relation;
	public City city;
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.CITY_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Cidade");
			ControllerCityModal controllerCityModal = (ControllerCityModal) loader.getController();
			controllerCityModal.setNewCity(newCity);
			controllerCityModal.setRelation(relation);
			controllerCityModal.setCity(city);
			controllerCityModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Boolean getNewCity() {
		return newCity;
	}

	public void setNewCity(Boolean newCity) {
		this.newCity = newCity;
	}

	public ControllerCityRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerCityRelationManager relation) {
		this.relation = relation;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
}
