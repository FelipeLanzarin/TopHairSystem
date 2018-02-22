package br.ths.controllers.cashier;

import java.net.URL;
import java.util.ResourceBundle;

import br.ths.beans.Cashier;
import br.ths.beans.Transaction;
import br.ths.beans.manager.OrderManager;
import br.ths.beans.manager.TransactionManager;
import br.ths.tools.log.LogTools;
import fx.tools.mask.MaskMoney;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerTransactionModal implements Initializable{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";

	@FXML private Label labelTitle;
	@FXML private MaskMoney textValue;
	@FXML private TextField textDescription;
	@FXML private TextField textType;
	
	private Stage stage;
	private Boolean newTransaction;
	private Transaction transaction;
	private ControllerCashierRelationManager relation;
	private Double lastValue;
	private String type;
	private Cashier cashier;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(transaction == null){
				transaction = new Transaction();
			}
			transaction.setDescription(textDescription.getText());
			transaction.setType(TransactionManager.getType(textType.getText()));
			Double value = OrderManager.getValuePriceAsDouble(textValue.getText());
			if(transaction.getType().equals("output")){
				value = value *(-1);
			}
			transaction.setAmount(value);
			transaction.setCashier(cashier);
			if(newTransaction){
				if(TransactionManager.create(transaction)){
					stage.close();
					relation.messageSucess("Movimentação cadastrada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar movimentação!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(TransactionManager.update(transaction, lastValue)){
					stage.close();
					relation.messageSucess("Movimentação alterada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar movimentação!");
					dialogoInfo.showAndWait();
					transaction.setAmount(lastValue);
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
			}else{
				Double value = OrderManager.getValuePriceAsDouble(textValue.getText());
				int compare = value.compareTo(0.0d);
				if(compare == 0){
					textValue.setStyle(STYLE_ERROR);
					valid = false;
				}
			}
			if(textDescription.getText().isEmpty()){
				textDescription.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
		
	}
	
	private void populateTransaction(Transaction transaction){
		try {
			textDescription.setText(transaction.getDescription());
			Double value = transaction.getAmount();
			lastValue = transaction.getAmount();
			if(transaction.getType().equals("output")){
				value = value *(-1);
			}
			textValue.setText(OrderManager.getValuePriceAsString(value));
			textType.setText(getType(transaction.getType()));
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private String getType(String stateTransaction){
		try {
			return TransactionManager.getDescriptionType(stateTransaction);
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return "";
	}
	
	public void actionValue(){
		textValue.setStyle("");
	}
	
	public void actionDescription(){
		textDescription.setStyle("");
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Boolean getNewTransaction() {
		return newTransaction;
	}

	public void setNewTransaction(Boolean newTransaction) {
		this.newTransaction = newTransaction;
		if(newTransaction){
			labelTitle.setText("Cadastrar "+ labelTitle.getText());
		}else{
			labelTitle.setText("Alterar "+ labelTitle.getText());
		}
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
		if(transaction != null){
			populateTransaction(transaction);
		}
	}

	public ControllerCashierRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerCashierRelationManager relation) {
		this.relation = relation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		if(type != null){
			textType.setText(type);
		}
	}

	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
}
