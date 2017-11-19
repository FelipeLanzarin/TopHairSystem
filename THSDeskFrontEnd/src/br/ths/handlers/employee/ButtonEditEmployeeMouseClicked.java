package br.ths.handlers.employee;

import br.ths.beans.Employee;
import br.ths.controllers.branch.employee.ControllerEmployeeRelationManager;
import br.ths.screens.branch.employee.ScreeanEmployeeModal;
import br.ths.tools.log.LogTools;
import fx.tools.action.EventAction;
import javafx.stage.Stage;

public class ButtonEditEmployeeMouseClicked implements EventAction {
	
	private ControllerEmployeeRelationManager controller;
	private Employee obj;
	
	@Override
	public void action() {
		try {
			ScreeanEmployeeModal scream = new ScreeanEmployeeModal();
			scream.setNewEmployee(false);
			scream.setRelation(controller);
			scream.setEmployee(obj);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}

	public ControllerEmployeeRelationManager getController() {
		return controller;
	}

	public void setController(ControllerEmployeeRelationManager controller) {
		this.controller = controller;
	}

	public Employee getObj() {
		return obj;
	}

	public void setObj(Employee obj) {
		this.obj = obj;
	}
	
	
}
