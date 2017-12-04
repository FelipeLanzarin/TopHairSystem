package br.ths.screens.branch.catalog.product;

import java.net.URL;

import br.ths.beans.Product;
import br.ths.controllers.catalog.product.ControllerProductModal;
import br.ths.controllers.catalog.product.ControllerProductRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenProductModal extends Application {
	
	public Boolean newProduct;
	public ControllerProductRelationManager relation;
	public Product produtc;
	
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PRODUCT_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Produto");
			ControllerProductModal controllerProductModal = (ControllerProductModal) loader.getController();
			controllerProductModal.setNewProduct(newProduct);
			controllerProductModal.setRelation(relation);
			controllerProductModal.setProduct(produtc);
			controllerProductModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Boolean getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
	}

	public ControllerProductRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerProductRelationManager relation) {
		this.relation = relation;
	}

	public Product getProdutc() {
		return produtc;
	}

	public void setProdutc(Product produtc) {
		this.produtc = produtc;
	}

	
}
