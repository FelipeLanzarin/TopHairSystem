package br.ths.screens.branch.employee;

import java.net.URL;

import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreeanEmployeeModal extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conex�o com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.EMPLOYEE_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Cadastrar Funcion�rio");
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
	
}