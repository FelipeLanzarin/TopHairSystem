package br.ths.handlers.city;

import br.ths.beans.City;
import br.ths.tools.log.LogTools;
import fx.tools.action.EventAction;
import fx.tools.controller.GenericController;

public class ButtonSelectCityMouseClicked implements EventAction {
	
	private GenericController genericController;
	private GenericController controllerCities;
	private City city;
	
	@Override
	public void action() {
		try {
			genericController.selectCity(city);
			controllerCities.getStage().close();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}

	public GenericController getGenericController() {
		return genericController;
	}

	public void setGenericController(GenericController genericController) {
		this.genericController = genericController;
	}

	public GenericController getControllerCities() {
		return controllerCities;
	}

	public void setControllerCities(GenericController controllerCities) {
		this.controllerCities = controllerCities;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
}
