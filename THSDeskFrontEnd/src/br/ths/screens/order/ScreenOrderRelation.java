package br.ths.screens.order;

import java.net.URL;

import br.ths.controllers.order.ControllerOrderRelation;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenOrderRelation extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.ORDER_RELATION);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			ControllerOrderRelation control = (ControllerOrderRelation) loader.getController();
			control.setStage(stage);
			control.createTable();
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
	
}
