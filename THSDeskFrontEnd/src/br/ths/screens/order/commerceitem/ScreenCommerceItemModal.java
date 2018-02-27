package br.ths.screens.order.commerceitem;

import java.net.URL;

import br.ths.beans.CommerceItem;
import br.ths.beans.Order;
import br.ths.beans.Product;
import br.ths.controllers.order.ControllerOrderModal;
import br.ths.controllers.order.commerceitem.ControllerCommerceItemModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCommerceItemModal extends Application {
	
	private Boolean newCommerceItem;
	private ControllerOrderModal orderModal;
	private Product produtc;
	private CommerceItem commerceItem;
	private Order order;
	private Stage stageCatalog;
	private Boolean onlySee;
	
	@Override
	public void start(Stage stage) {
		try {
//			LogTools.logDebug("arquivo = "+ XmlPathUtils.COMMERCE_ITEM_MODAL);
			URL arquivoFXML = getClass().getResource(XmlPathUtils.COMMERCE_ITEM_MODAL);
//			LogTools.logDebug(arquivoFXML.toString());
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Comprar produto");
			ControllerCommerceItemModal controllerCommerceItemModal = (ControllerCommerceItemModal) loader.getController();
			controllerCommerceItemModal.setNewCommerceItem(newCommerceItem);
			controllerCommerceItemModal.setOrderModal(orderModal);
			controllerCommerceItemModal.setOrder(order);
			controllerCommerceItemModal.setCommerceItem(commerceItem);
			controllerCommerceItemModal.setProduct(produtc);
			controllerCommerceItemModal.setStage(stage);
			controllerCommerceItemModal.setStageCatalog(stageCatalog);
			controllerCommerceItemModal.setOnlySee(onlySee);
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Stage getStageCatalog() {
		return stageCatalog;
	}

	public void setStageCatalog(Stage stageCatalog) {
		this.stageCatalog = stageCatalog;
	}

	public Boolean getOnlySee() {
		return onlySee;
	}

	public void setOnlySee(Boolean onlySee) {
		this.onlySee = onlySee;
	}
	
}
