package br.ths.screens.cashier;
	
import java.net.URL;

import br.ths.beans.manager.CashierManager;
import br.ths.controllers.cashier.ControllerCashierRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ScreenCashier extends Application {
	
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.CASHIER);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Caixa");
			ControllerCashierRelationManager controller = (ControllerCashierRelationManager) loader.getController();
			controller.setCashier(CashierManager.getCashier(1));
			controller.createTable();
			controller.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
	
}
