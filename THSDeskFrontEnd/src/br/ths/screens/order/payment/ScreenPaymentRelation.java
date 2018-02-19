package br.ths.screens.order.payment;

import java.net.URL;

import br.ths.beans.Order;
import br.ths.controllers.order.payment.ControllerPaymentRelation;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenPaymentRelation extends Application {
	
	private Order order;
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PAYMENT_RELATION_MANAGER);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			ControllerPaymentRelation control = (ControllerPaymentRelation) loader.getController();
			control.setOrder(order);
			control.setStage(stage);
			control.createTable();
			stage.setResizable(false);
			stage.setTitle("Metodo de Pagamento");
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
