package br.ths.screens.order.image;

import java.net.URL;

import br.ths.beans.Order;
import br.ths.controllers.order.image.ControllerImageRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenImageRelationManager extends Application {
	
	private Order order;
		
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.IMAGE_RELATION);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			ControllerImageRelationManager control = (ControllerImageRelationManager) loader.getController();
			control.setOrder(order);
			control.setStage(stage);
//			control.showImage();
			control.createTable();
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
