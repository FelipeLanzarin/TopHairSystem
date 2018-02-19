package br.ths.controllers.order.payment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.Installment;
import br.ths.beans.Order;
import br.ths.beans.Payment;
import br.ths.beans.PaymentMethod;
import br.ths.beans.manager.OrderManager;
import br.ths.beans.manager.PaymentManager;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerPaymentMethodModal implements Initializable{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";

//	@FXML private Label labelTitle;
	@FXML private TextField textValue;
	@FXML private ComboBox<String> comboType;
	
	private Stage stage;
	private Boolean newPaymentMethod;
	private PaymentMethod paymentMethod;
	private Order order;
	private Payment payment;
	private Installment installment;
	private List<String> types;
	private GenericController relation;
	private Double lastValue;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(paymentMethod == null){
				paymentMethod = new PaymentMethod();
			}
			paymentMethod.setType(comboType.getSelectionModel().getSelectedItem());
			paymentMethod.setAmount(OrderManager.getValuePriceAsDouble(textValue.getText()));
			paymentMethod.setOrder(order);
			paymentMethod.setPayment(payment);
			paymentMethod.setInstallment(installment);
			if(newPaymentMethod){
				try{
					if(PaymentManager.create(paymentMethod)){
						stage.close();
						relation.updateTable();
					}else{
						Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
						dialogoInfo.setTitle("Erro!");
						dialogoInfo.setHeaderText("Erro ao cadastrar Pagemento!");
						dialogoInfo.showAndWait();
					}
				}catch (ManagersExceptions e) {
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText(e.getExcepetionMessage());
					dialogoInfo.showAndWait();
				}
			}else{
				try{
					if(PaymentManager.update(paymentMethod, lastValue)){
						stage.close();
						relation.updateTable();
					}else{
						Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
						dialogoInfo.setTitle("Erro!");
						dialogoInfo.setHeaderText("Erro ao alterar Pagamento!");
						dialogoInfo.showAndWait();
						paymentMethod.setAmount(lastValue);
					}
				}catch (ManagersExceptions e) {
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText(e.getExcepetionMessage());
					dialogoInfo.showAndWait();
					paymentMethod.setAmount(lastValue);
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private Boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textValue.getText().isEmpty()){
				textValue.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
		
	}
	
	private void populatePaymentMethod(PaymentMethod paymentMethod){
		try {
			lastValue = paymentMethod.getAmount();
			textValue.setText(OrderManager.getValuePriceAsString(paymentMethod.getAmount()));
			comboType.setValue(getType(paymentMethod.getType()));
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private String getType(String statePaymentMethod){
		try {
			for (String string : types) {
				if(statePaymentMethod.equalsIgnoreCase(string)){
					return string;
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return "";
	}
	
	public void actionValue(){
		textValue.setStyle("");
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Boolean getNewPaymentMethod() {
		return newPaymentMethod;
	}

	public void setNewPaymentMethod(Boolean newPaymentMethod) {
		this.newPaymentMethod = newPaymentMethod;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
		if(paymentMethod != null){
			populatePaymentMethod(paymentMethod);
		}
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public GenericController getRelation() {
		return relation;
	}

	public void setRelation(GenericController relation) {
		this.relation = relation;
		types = new ArrayList<>();
		types.add("Dinheiro");
		types.add("Cartão de Crédito");
		types.add("Cheque");
		types.add("Outro");
		
		comboType.getItems().addAll(types);
		comboType.getSelectionModel().select("Dinheiro");
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
		if(newPaymentMethod){
			if(installment != null){
				Double amountLeft = installment.getAmount() - installment.getAmountPayed();
				textValue.setText(OrderManager.getValuePriceAsString(amountLeft));
			}else{
				textValue.setText(OrderManager.getAmountAsString(order));
			}
		}
	}
	
}
