package br.ths.handlers.employee;

import br.ths.beans.Employee;
import br.ths.beans.manager.EmployeeManager;
import br.ths.controllers.branch.employee.ControllerEmployeeRelationManager;
import br.ths.tools.log.LogTools;
import fx.tools.action.EventAction;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ButtonDeleteEmployeeMouseClicked implements EventAction{
	
	private Employee obj;
	private ControllerEmployeeRelationManager controller;
	
	@Override
	public void action() {
		try {
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir o funcionário "+ obj.getName()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				if(EmployeeManager.delete(obj)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Funcionário excluído com sucesso");
					dialog.showAndWait();
					controller.updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro ao excluir Funcionário!");
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}

	public Employee getObj() {
		return obj;
	}

	public void setObj(Employee obj) {
		this.obj = obj;
	}



	public ControllerEmployeeRelationManager getController() {
		return controller;
	}

	public void setController(ControllerEmployeeRelationManager controller) {
		this.controller = controller;
	}
	
}
