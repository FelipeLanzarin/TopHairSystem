package br.ths.screens.branch.catalog.product;

import java.net.URL;

import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenProductModal extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PRODUCT_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Categoria");
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}
}
