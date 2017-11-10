package br.ths.screens.branch.employee;

import java.net.URL;

import br.ths.controllers.branch.employee.ControllerEmployeeModal;
import br.ths.controllers.branch.employee.ControllerEmployeeRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreeanEmployeeModal extends Application {
	
	public Boolean newEmployee;
	public ControllerEmployeeRelationManager relation;
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conex�o com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.EMPLOYEE_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Funcion�rio");
			ControllerEmployeeModal controllerEmployeeModal = (ControllerEmployeeModal) loader.getController();
			controllerEmployeeModal.setNewEmployee(newEmployee);
			controllerEmployeeModal.setRelation(relation);
			controllerEmployeeModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Boolean getNewEmployee() {
		return newEmployee;
	}

	public void setNewEmployee(Boolean newEmployee) {
		this.newEmployee = newEmployee;
	}

	public ControllerEmployeeRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerEmployeeRelationManager relation) {
		this.relation = relation;
	}
	
}
