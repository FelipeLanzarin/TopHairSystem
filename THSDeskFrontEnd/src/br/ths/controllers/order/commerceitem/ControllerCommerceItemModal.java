package br.ths.controllers.order.commerceitem;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.CommerceItem;
import br.ths.beans.Order;
import br.ths.beans.Product;
import br.ths.beans.manager.CommerceItemManager;
import br.ths.beans.manager.OrderManager;
import br.ths.beans.manager.ProductManager;
import br.ths.controllers.order.ControllerOrderModal;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskMoney;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerCommerceItemModal extends GenericController{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";

	@FXML private Label labelTitle;
	@FXML private TextField textId;
	@FXML private TextField textName;
	@FXML private TextField textType;
	@FXML private TextField textUn;
	@FXML private MaskMoney textDiscount;
	@FXML private MaskMoney textPrice;
	@FXML private MaskTextField textQuantity;
	@FXML private MaskMoney textAmount;
	@FXML private TextArea textDescription;
	@FXML private Button buttonAdd;
	
	private Stage stage;
	private Stage stageCatalog;
	private Boolean newCommerceItem;
	private CommerceItem commerceItem;
	private List<String> states;
	private ControllerOrderModal orderModal;
	private Product product;
	private Order order;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textDiscount.setGenericController(this);
		textAmount.setGenericController(this);
		textQuantity.setGenericController(this);
	}

  
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(commerceItem == null){
				commerceItem = new CommerceItem();
			}
			commerceItem.setAmount(CommerceItemManager.getValuePriceAsDouble(textAmount.getText()));
			commerceItem.setDiscount(CommerceItemManager.getValuePriceAsDouble(textDiscount.getText()));
			commerceItem.setNote(textDescription.getText());
			commerceItem.setQuantity(CommerceItemManager.getQuantityAsInteger(textQuantity.getText()));
			commerceItem.setUnitPrice(CommerceItemManager.getValuePriceAsDouble(textPrice.getText()));
			if(newCommerceItem){
				try{
					commerceItem.setCreationDate(new Date());
					commerceItem.setOrder(order);
					commerceItem.setProduct(product);
					commerceItem.setCostPrice(product.getCostPrice());
					if(CommerceItemManager.create(commerceItem)){
						if(stageCatalog != null){
							stageCatalog.close();
						}
						stage.close();
						orderModal.updateTable(null,true);
					}else{
						Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
						dialogoInfo.setTitle("Erro!");
						dialogoInfo.setHeaderText("Erro ao inserir produto à compra!");
						dialogoInfo.showAndWait();
					}
				}catch (ManagersExceptions me) {
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao inserir produto à compra!");
					dialogoInfo.setContentText(me.getExcepetionMessage());
					dialogoInfo.showAndWait();
				}
			}else{
				try{
					if(CommerceItemManager.update(commerceItem)){
						if(stageCatalog != null){
							stageCatalog.close();
						}
						stage.close();
						orderModal.updateTable(null,true);
					}else{
						Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
						dialogoInfo.setTitle("Erro!");
						dialogoInfo.setHeaderText("Erro ao alterar item!");
						dialogoInfo.showAndWait();
					}
				}catch (ManagersExceptions me) {
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar item!");
					dialogoInfo.setContentText(me.getExcepetionMessage());
					dialogoInfo.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public boolean validateFiedls(){
		Boolean valid = true;
		try{
			if(textQuantity.getText().isEmpty()){
				textQuantity.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textAmount.getText().isEmpty()){
				textAmount.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
	}
	
	private void populateCommerceItem(CommerceItem commerceItem){
		try {
			if(commerceItem == null){
				return;
			}
			Product product = commerceItem.getProduct();
			textId.setText(product.getId()+"");
			textName.setText(product.getName());
			textUn.setText(product.getUnType());
			textType.setText(product.getType());
			textPrice.setText(CommerceItemManager.getUnitPriceAsString(commerceItem));
			textAmount.setText(CommerceItemManager.getAmountAsString(commerceItem));
			textDiscount.setText(CommerceItemManager.getDiscountAsString(commerceItem));
			textQuantity.setText(commerceItem.getQuantity()+"");
			textDescription.setText(commerceItem.getNote());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void populateProduct(Product product){
		try {
			if(product == null){
				return;
			}
			textId.setText(product.getId()+"");
			textName.setText(product.getName());
			textUn.setText(product.getUnType());
			textType.setText(product.getType());
			textPrice.setText(ProductManager.getUnitPriceAsString(product));
			textAmount.setText(ProductManager.getUnitPriceAsString(product));
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void actionUnitValue(){
		try{
			if(textQuantity.getText().isEmpty()){
				return;
			}
			textQuantity.setStyle("");
			Integer newQuantity = Integer.parseInt(textQuantity.getText());
			if(newQuantity<1){
				textQuantity.setText(commerceItem.getQuantity()+"");
			}else{
				recalculateCommerceItemByQuantity(newQuantity);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void recalculateCommerceItemByQuantity(Integer newQuantity){
		if(newQuantity == null || newQuantity < 1){
			return;
		}
		Double unitPrice = CommerceItemManager.getValuePriceAsDouble(textPrice.getText());
		Double discount = CommerceItemManager.getValuePriceAsDouble(textDiscount.getText());
		Double newFinalAmount = unitPrice * newQuantity;
		if(newFinalAmount < discount){
			textDiscount.setText(CommerceItemManager.getValuePriceAsString(0.0D));
			textAmount.setText(CommerceItemManager.getValuePriceAsString(newFinalAmount));
		}else{
			textAmount.setText(CommerceItemManager.getValuePriceAsString(newFinalAmount-discount));
		}
	}
	
	public void plusQuantity(){
		try{
			textQuantity.setStyle("");
			String qt = textQuantity.getText();
			if(qt.isEmpty()){
				textQuantity.setText("1");
				recalculateCommerceItemByQuantity(1);
			}else{
				Integer qtd = Integer.parseInt(qt);
				qtd += 1;
				textQuantity.setText(qtd+"");
				recalculateCommerceItemByQuantity(qtd);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void lessQuantity(){
		try{
			textQuantity.setStyle("");
			String qt = textQuantity.getText();
			if(qt.isEmpty() || qt.equals("1")){
				return;
			}else{
				Integer qtd = Integer.parseInt(qt);
				qtd -= 1;
				textQuantity.setText(qtd+"");
				recalculateCommerceItemByQuantity(qtd);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void actionAmount(){
		textAmount.setStyle("");
		try{
			String amount = textAmount.getText();
			if(amount.isEmpty()){
				textDiscount.setText("0,00");
				return;
			}else{
				Double finalPrice = CommerceItemManager.getValuePriceAsDouble(amount);
				Integer qt = 1;
				Double unitPrice = 0.0D;
				if(commerceItem != null){//produto novo
					if(commerceItem.getUnitPrice() != null){
						unitPrice = commerceItem.getUnitPrice();
					}else{
						unitPrice = product.getPrice();
					}
				}else{
					unitPrice = product.getPrice();
				}
				
				if(!textQuantity.getText().isEmpty()){
					qt = Integer.parseInt(textQuantity.getText());
				}
				Double discount = (unitPrice*qt) - finalPrice;
				if(discount>=0){
					textDiscount.setText(CommerceItemManager.getValuePriceAsString(discount));
				}else{
					textDiscount.setText("0,00");
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void actionDiscount(){
		textDiscount.setStyle("");
		try{
			String discount = textDiscount.getText();
			if(discount.isEmpty()){
				textAmount.setText(textPrice.getText());
				return;
			}else{
				Double discountAmount = CommerceItemManager.getValuePriceAsDouble(discount);
				Integer qt = 1;
				Double unitPrice = 0.0D;
				if(commerceItem != null){//produto novo
					if(commerceItem.getUnitPrice() != null){
						unitPrice = commerceItem.getUnitPrice();
					}else{
						unitPrice = product.getPrice();
					}
				}else{
					unitPrice = product.getPrice();
				}
				if(!textQuantity.getText().isEmpty()){
					qt = Integer.parseInt(textQuantity.getText());
				}
				Double finalPrice = (unitPrice*qt) - discountAmount;
				if(finalPrice>=0){
					textAmount.setText(CommerceItemManager.getValuePriceAsString(finalPrice));
				}else{
					textAmount.setText("0,00");
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Boolean getNewCommerceItem() {
		return newCommerceItem;
	}

	public void setNewCommerceItem(Boolean newCommerceItem) {
		this.newCommerceItem = newCommerceItem;
		if(!newCommerceItem){
			buttonAdd.setText("Alterar");
		}
	}

	public CommerceItem getCommerceItem() {
		return commerceItem;
	}

	public void setCommerceItem(CommerceItem commerceItem) {
		this.commerceItem = commerceItem;
		if(commerceItem != null){
			populateCommerceItem(commerceItem);
		}
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public ControllerOrderModal getOrderModal() {
		return orderModal;
	}

	public void setOrderModal(ControllerOrderModal orderModal) {
		this.orderModal = orderModal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		if(product != null){
			if(!findProductInOrder(product)){
				populateProduct(product);
			}
		}
	}
	
	private boolean findProductInOrder(Product product){
		if(product == null || order == null){
			return false;
		}
		List<CommerceItem> cis = CommerceItemManager.getCommerceItemsByOrder(order);
		if(cis != null){
			for (CommerceItem ci : CommerceItemManager.getCommerceItemsByOrder(order)) {
				if(ci.getProduct().getId().equals(product.getId())){
					ci.setCostPrice(product.getCostPrice());
					ci.setUnitPrice(product.getPrice());
					setCommerceItem(CommerceItemManager.addQuantityOnCommerceItem(ci, 1));
					setNewCommerceItem(false);
					return true;
				}
			}
		}
		
		return false;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
		if(order == null){
			try {
				order = OrderManager.createForProfile(null);
			} catch (ManagersExceptions e) {
				LogTools.logError(e);
			}
		}
	}

	public Stage getStageCatalog() {
		return stageCatalog;
	}


	public void setStageCatalog(Stage stageCatalog) {
		this.stageCatalog = stageCatalog;
	}
	
}
