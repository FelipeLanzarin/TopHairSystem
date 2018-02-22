package br.ths.controllers.cashier;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.Cashier;
import br.ths.beans.Transaction;
import br.ths.beans.manager.OrderManager;
import br.ths.beans.manager.PaymentManager;
import br.ths.beans.manager.TransactionManager;
import br.ths.screens.cashier.ScreenTransactionModal;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerCashierRelationManager extends GenericController{

	@FXML private DatePicker textDate;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	@FXML private Label labelValue;
	@FXML private Label labelValueCashier;
	@FXML private Label labelValueDay;
	@FXML private Label labelValueCashierDay;
	@FXML private Menu configuration;
	@FXML private TableView<Transaction> table;
	@FXML private TableColumn<Transaction, String> columnOne;
	@FXML private TableColumn<Transaction, String> columnTwo;
	@FXML private TableColumn<Transaction, String> columnThree;
	@FXML private TableColumn<Transaction, String> columnFour;
	
	private Cashier cashier;
	private List<Transaction> list;
	private Date date;
	private Transaction transactionSelected;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		date = calendar.getTime();    
	}
	
	public void updateTable(List<Transaction> listTransactions, Date date){
		try {
			if(listTransactions== null){
				listTransactions = TransactionManager.getTransactionsByDate(date);
			}
			Instant instant = Instant.ofEpochMilli(date.getTime());
		    LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		    textDate.setValue(localDate);
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listTransactions));
			populateValuesByDay(listTransactions);
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = TransactionManager.getTransactionsByDate(new Date());
			columnOne.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Transaction, String> transaction) {
					String type = "Saída";
					if("input".equals(transaction.getValue().getType())){
						type = "Entrada";
					}
					return new SimpleStringProperty(type);
				}
			});
			columnTwo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Transaction, String> transaction) {
					return new SimpleStringProperty(OrderManager.getTimeAsString(transaction.getValue().getTime()));
				}
			});
			columnThree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Transaction, String> transaction) {
					if(transaction.getValue().getPaymentMethod() != null){
						return new SimpleStringProperty(PaymentManager.getDescriptionByPaymentMethod(transaction.getValue().getPaymentMethod()));
					}
					return new SimpleStringProperty(transaction.getValue().getDescription());
				}
			});
			columnFour.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Transaction, String> transaction) {
					DecimalFormat df = new DecimalFormat("###,###,##0.00");
					String amount= "0,00";
					try{
						amount = df.format(transaction.getValue().getAmount());
					}catch (Exception e) {
					}
					return new SimpleStringProperty("R$ "+amount);
				}
			});
			date = new Date();
			updateTable(list, date);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	/**
	 * Metodo para atualizar a agenda de acordo com a data que está selecionada
	 */
	public void selectDate(){
		if(textDate.getValue() != null){
			LocalDate ld = textDate.getValue();
			Calendar c =  Calendar.getInstance();
			c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
			date = c.getTime();
			updateTable(null, c.getTime());
		}
	}
	
	public void clickNext(){
		if(textDate.getValue() != null){
			LocalDate ld = textDate.getValue();
			Calendar c =  Calendar.getInstance();
			c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
			c.add(Calendar.DAY_OF_MONTH, 1);
			date = c.getTime();
			updateTable(null, c.getTime());
		}
	}
	
	public void clickPreview(){
		if(textDate.getValue() != null){
			LocalDate ld = textDate.getValue();
			Calendar c =  Calendar.getInstance();
			c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
			c.add(Calendar.DAY_OF_MONTH, -1);
			date = c.getTime();
			updateTable(null, c.getTime());
		}
	}
	
	public void clickButtonNew(){
		try{
			ScreenTransactionModal screen = new ScreenTransactionModal();
			screen.setNewTransaction(true);
			screen.setRelation(this);
			screen.setType("Entrada");
			screen.setCashier(cashier);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonNewOutput(){
		try{
			ScreenTransactionModal screen = new ScreenTransactionModal();
			screen.setNewTransaction(true);
			screen.setRelation(this);
			screen.setType("Saída");
			screen.setCashier(cashier);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			if(transactionSelected == null){
				Alert dialog = new Alert(Alert.AlertType.WARNING);
				dialog.setTitle("Info!");
				dialog.setHeaderText("Selecione uma transação!");
				dialog.showAndWait();
				return;
			}
			ScreenTransactionModal screen = new ScreenTransactionModal();
			screen.setNewTransaction(false);
			screen.setRelation(this);
			screen.setCashier(cashier);
			screen.setTransaction(transactionSelected);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try{
			if(transactionSelected == null){
				Alert dialog = new Alert(Alert.AlertType.WARNING);
				dialog.setTitle("Info!");
				dialog.setHeaderText("Selecione uma transação para excluir!");
				dialog.showAndWait();
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja remover o pagamento "+ transactionSelected.getDescription()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				transactionSelected.setCashier(cashier);
				if(TransactionManager.delete(transactionSelected)){
					updateTable(null, date);
					populateValues(cashier);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro ao remover transação!");
					dialog.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			transactionSelected = table.getSelectionModel().getSelectedItem();
			if(transactionSelected.getPaymentMethod() != null){
				transactionSelected = null;
				disableButtons(true);
			}else{
				disableButtons(false);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	private void disableButtons(Boolean disable){
		buttonEdit.setDisable(disable);
		buttonDelete.setDisable(disable);
	}
	
	private void populateValues(Cashier cashier){
		Double amountInCashier = cashier.getAmountInCashier();
		Double balance = cashier.getBalance();
		if(amountInCashier<0){
			labelValueCashier.setTextFill(Color.web("#ff0000"));
			amountInCashier = amountInCashier * (-1);
		}else{
			labelValueCashier.setTextFill(Color.web("#008000"));
		}
		if(cashier.getBalance()<0){
			labelValue.setTextFill(Color.web("#ff0000"));
			balance = balance * (-1);
		}else{
			labelValue.setTextFill(Color.web("#008000"));
		}
		labelValue.setText(OrderManager.getValuePriceAsString(balance));
		labelValueCashier.setText(OrderManager.getValuePriceAsString(amountInCashier));
	}
	
	public void messageSucess(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setTitle("Sucesso");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
		updateTable(null, date);
		populateValues(cashier);
	}
	
	private void populateValuesByDay(List<Transaction> list){
		Double sumAll = 0.0d;
		Double sumInCashier = 0.0d;
		for (Transaction transaction : list) {
			sumAll+=transaction.getAmount();
			sumAll = OrderManager.roundValue(sumAll);
			if(transaction.getPaymentMethod() == null){
				sumInCashier += transaction.getAmount();
				sumInCashier = OrderManager.roundValue(sumInCashier);
			}else if(TransactionManager.transactionIsMoney(transaction)){
				sumInCashier += transaction.getAmount();
				sumInCashier = OrderManager.roundValue(sumInCashier);
			}
		}
		if(sumAll<0){
			labelValueDay.setTextFill(Color.web("#ff0000"));
			sumAll = sumAll * (-1);
		}else{
			labelValueDay.setTextFill(Color.web("#008000"));
		}
		if(sumInCashier<0){
			labelValueCashierDay.setTextFill(Color.web("#ff0000"));
			sumInCashier = sumInCashier * (-1);
		}else{
			labelValueCashierDay.setTextFill(Color.web("#008000"));
		}
		labelValueDay.setText(OrderManager.getValuePriceAsString(sumAll));
		labelValueCashierDay.setText(OrderManager.getValuePriceAsString(sumInCashier));
	}
	
	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
		populateValues(cashier);
	}	

}
