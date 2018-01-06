package br.ths.screens.order.commerceitem;

import java.net.URL;

import br.ths.beans.CommerceItem;
import br.ths.beans.Product;
import br.ths.controllers.order.ControllerOrderModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCommerceItemModal extends Application {
	
	public Boolean newCommerceItem;
	public ControllerOrderModal orderModal;
	public Product produtc;
	public CommerceItem commerceItem;
	
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
//			ControllerProductModal controllerProductModal = (ControllerProductModal) loader.getController();
//			controllerProductModal.setNewProduct(newProduct);
//			controllerProductModal.setRelation(relation);
//			controllerProductModal.setProduct(produtc);
//			controllerProductModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Boolean getNewCommerceItem() {
		return newCommerceItem;
	}

	public void setNewCommerceItem(Boolean newCommerceItem) {
		this.newCommerceItem = newCommerceItem;
	}

	public ControllerOrderModal getOrderModal() {
		return orderModal;
	}

	public void setOrderModal(ControllerOrderModal orderModal) {
		this.orderModal = orderModal;
	}

	public Product getProdutc() {
		return produtc;
	}

	public void setProdutc(Product produtc) {
		this.produtc = produtc;
	}

	public CommerceItem getCommerceItem() {
		return commerceItem;
	}

	public void setCommerceItem(CommerceItem commerceItem) {
		this.commerceItem = commerceItem;
	}

}
