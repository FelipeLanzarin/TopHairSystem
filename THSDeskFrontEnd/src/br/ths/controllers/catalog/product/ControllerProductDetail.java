package br.ths.controllers.catalog.product;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.Product;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskMoney;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerProductDetail extends GenericController{
	
	private static final String SEP = " - ";
	private static final DecimalFormat df = new DecimalFormat("###,###,##0.00");

	@FXML private Label labelTitle;
	@FXML private TextField textCategory;
	@FXML private TextField textSubCategory;
	@FXML private MaskTextField textId;
	@FXML private TextField textName;
	@FXML private TextField textType;
	@FXML private TextField textUn;
	@FXML private MaskMoney textCostPrice;
	@FXML private MaskMoney textPrice;
	@FXML private MaskTextField textUnitValue;
	@FXML private TextArea textDescription;
	
	private Stage stage;
	private Product product;
	private List<String> states;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textCostPrice.setGenericController(this);
		textPrice.setGenericController(this);
		textUnitValue.setGenericController(this);
	}
	
	private void populateProduct(Product product){
		try {
			textId.setText(product.getId().toString());
			textName.setText(product.getName());
			textDescription.setText(product.getDescription());
			if(product.getSubCategory() != null){	
				textCategory.setText(product.getSubCategory().getCategory().getId()+ SEP +product.getSubCategory().getCategory().getName());
				textSubCategory.setText(product.getSubCategory().getId()+ SEP +product.getSubCategory().getName());
			}
			textCostPrice.setText(df.format(product.getCostPrice()));
			textPrice.setText(df.format(product.getPrice()));
			textUnitValue.setText(product.getUn().toString());
			
			if(product.getType() != null && "service".equals(product.getType())){
				textType.setText("Serviço");
			}else{
				textType.setText("Produto");
			}
			
			switch (product.getUnType()) {
			case "UN":
				textUn.setText("Unidade");
				break;
			case "ML":
				textUn.setText("ML");
				break;
			case "LT":
				textUn.setText("Litros");
				break;
			}
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void actionId(){
		textId.setStyle("");
	}
	
	public void actionName(){
		textName.setStyle("");
	}
	
	public void actionCostPrice(){
		textCostPrice.setStyle("");
	}
	
	public void actionPrice(){
		textPrice.setStyle("");
	}
	
	public void actionUnitValue(){
		textUnitValue.setStyle("");
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		if(product != null){
			populateProduct(product);
		}
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}
	
}
