package br.ths.utils;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.City;
import br.ths.beans.Employee;
import br.ths.beans.manager.CityManager;
import br.ths.beans.manager.EmployeeManager;
import br.ths.controllers.branch.employee.ControllerEmployeeRelationManager;
import br.ths.handlers.city.ButtonSelectCityMouseClicked;
import br.ths.handlers.employee.ButtonDeleteEmployeeMouseClicked;
import br.ths.handlers.employee.ButtonEditEmployeeMouseClicked;
import br.ths.utils.beans.CityRown;
import br.ths.utils.beans.EmployeeManagerRown;
import fx.tools.button.ButtonEventUtils;
import fx.tools.controller.GenericController;
import fx.tools.mouse.MouseEnventControler;
import javafx.scene.Scene;

public class TableViewUtils {
	
	/**
	 * Retorna todas as cidades na lista no formato CityRown para ser utilizado na tabela
	 * @return
	 */
	public static List<CityRown> getCities(Scene scene, GenericController controller, GenericController cityController){
		List<CityRown> cityRowns = new ArrayList<>();
		for (City city : CityManager.getCities()) {
			CityRown cityRown = new CityRown();
			cityRown.setId(city.getId());
			cityRown.setName(city.getName());
			cityRown.setCountry(city.getCountry());
			cityRown.setUf(city.getUf());
//			cityRown.setCity(city);
			cityRowns.add(cityRown);
			
			ButtonSelectCityMouseClicked bec = new ButtonSelectCityMouseClicked();
			bec.setCity(city);
			bec.setGenericController(controller);
			bec.setControllerCities(cityController);
			cityRown.getSelect().setOnMouseClicked(MouseEnventControler.getMouseCliked(bec));
			ButtonEventUtils.setEvents(cityRown.getSelect(), scene);
		}
		return cityRowns;
	}
	
	/**
	 * Retorna todas as cidades na lista no formato CityRown para ser utilizado na tabela
	 * @return
	 */
	public static List<EmployeeManagerRown> getEmployeeManagers(Scene scene, ControllerEmployeeRelationManager controller){
		List<EmployeeManagerRown> list = new ArrayList<>();
		for (Employee obj : EmployeeManager.getEmployees()) {
			EmployeeManagerRown objRown = new EmployeeManagerRown();
			objRown.setId(obj.getId());
			objRown.setName(obj.getName());
			objRown.setCpf(obj.getCpf());
			objRown.setEmail(obj.getEmail());
			objRown.setTelephone(obj.getTelephone());
			objRown.setAddress(obj.getAddress());
			list.add(objRown);
			
			ButtonEditEmployeeMouseClicked bec = new ButtonEditEmployeeMouseClicked();
			bec.setObj(obj);
			bec.setController(controller);
			objRown.getEdit().setOnMouseClicked(MouseEnventControler.getMouseCliked(bec));
			ButtonEventUtils.setEvents(objRown.getEdit(), scene);
			
			ButtonDeleteEmployeeMouseClicked bd = new ButtonDeleteEmployeeMouseClicked();
			bd.setObj(obj);
			bd.setController(controller);
			objRown.getDelete().setOnMouseClicked(MouseEnventControler.getMouseCliked(bd));
			ButtonEventUtils.setEvents(objRown.getDelete(), scene);
		}
		return list;
	}
}
