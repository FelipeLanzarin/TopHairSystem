package br.ths.screens.branch.catalog.product;

import java.net.URL;

import br.ths.beans.Product;
import br.ths.controllers.catalog.product.ControllerProductDetail;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenProductDetail extends Application {
	
	private Product product;
	private Stage stageCatalog;
	
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PRODUCT_DETAIL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Produto");
			ControllerProductDetail controllerProductDetail = (ControllerProductDetail) loader.getController();
			controllerProductDetail.setProduct(product);
			controllerProductDetail.setStage(stage);
			controllerProductDetail.setStageCatalog(stageCatalog);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Stage getStageCatalog() {
		return stageCatalog;
	}

	public void setStageCatalog(Stage stageCatalog) {
		this.stageCatalog = stageCatalog;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
