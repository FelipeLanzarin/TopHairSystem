package br.ths.screens.order;

import java.net.URL;

import br.ths.beans.Order;
import br.ths.controllers.order.ControllerOrderModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import fx.tools.controller.GenericController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenOrderModal extends Application {
	private Order order;
	private GenericController lastController;
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.ORDER_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Pedido");
			ControllerOrderModal controller = (ControllerOrderModal) loader.getController();
			controller.setOrder(order);
			controller.setStage(stage);
			controller.setLastController(lastController);
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
	public GenericController getLastController() {
		return lastController;
	}
	public void setLastController(GenericController lastController) {
		this.lastController = lastController;
	}
}
