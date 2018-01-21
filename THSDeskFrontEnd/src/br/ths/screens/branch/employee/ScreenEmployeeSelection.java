package br.ths.screens.branch.employee;

import java.net.URL;

import br.ths.controllers.branch.employee.ControllerEmployeeSelection;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import fx.tools.controller.GenericController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenEmployeeSelection extends Application {
	
	private GenericController controller;
	
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.EMPLOYEE_SELECTION);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			ControllerEmployeeSelection control = (ControllerEmployeeSelection) loader.getController();
			control.setStage(stage);
			control.createTable();
			control.setController(controller);
			control.setStage(stage);
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public GenericController getController() {
		return controller;
	}

	public void setController(GenericController controller) {
		this.controller = controller;
	}

}
