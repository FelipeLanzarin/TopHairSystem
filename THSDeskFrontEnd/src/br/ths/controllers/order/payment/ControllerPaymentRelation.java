package br.ths.controllers.order.payment;

import java.util.List;

import br.ths.beans.Installment;
import br.ths.beans.Order;
import br.ths.beans.Payment;
import br.ths.beans.PaymentMethod;
import br.ths.beans.manager.OrderManager;
import br.ths.beans.manager.PaymentManager;
import br.ths.exceptions.ManagersExceptions;
import br.ths.screens.order.payment.ScreenPaymentMethodModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import fx.tools.controller.GenericController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerPaymentRelation extends GenericController{
	
	@FXML private Label labelTitle;
	@FXML private Label labelValue;
	@FXML private Label labelValuePayed;
	@FXML private Button buttonNew;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	@FXML private Button buttonFinishOrder;
	@FXML private TableView<PaymentMethod> table;
	@FXML private TableColumn<PaymentMethod, String> columnOne;
	@FXML private TableColumn<PaymentMethod, String> columnTwo;
	@FXML private TableColumn<PaymentMethod, String> columnThree;
	private Order order;
	private Payment payment;
	private Installment installment;
	private List<PaymentMethod> paymentMethods;
	private PaymentMethod paymentMethodSelect;
	
	public void clickButtonNew(){
		try{
			if(payment == null){
				payment = new Payment();
				payment.setOrder(order);
				PaymentManager.create(payment);
			}
			if(installment == null){
				installment = new Installment();
				installment.setOrder(order);
				installment.setPayment(payment);
				installment.setAmount(payment.getAmount());
				PaymentManager.create(installment);
			}
			ScreenPaymentMethodModal screen = new ScreenPaymentMethodModal();
			screen.setController(this);
			screen.setOrder(order);
			screen.setPayment(payment);
			screen.setInstallment(installment);
			screen.setNewPaymentMethod(true);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try{
			if(paymentMethodSelect == null){
				Alert dialog = new Alert(Alert.AlertType.WARNING);
				dialog.setTitle("Info!");
				dialog.setHeaderText("Selecione uma forma de pagamento para excluir!");
				dialog.showAndWait();
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja remover o pagamento "+ paymentMethodSelect.getId()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				paymentMethodSelect.setPayment(payment);
				paymentMethodSelect.setInstallment(installment);
				if(PaymentManager.delete(paymentMethodSelect)){
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro ao remover pagamento!");
					dialog.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit() {
		try{
			if(paymentMethodSelect == null){
				Alert dialog = new Alert(Alert.AlertType.WARNING);
				dialog.setTitle("Info!");
				dialog.setHeaderText("Selecione uma forma de pagamento para alterar!");
				dialog.showAndWait();
				return;
			}
			ScreenPaymentMethodModal screen = new ScreenPaymentMethodModal();
			screen.setController(this);
			screen.setOrder(order);
			screen.setPayment(payment);
			screen.setInstallment(installment);
			screen.setNewPaymentMethod(false);
			screen.setPaymentMethod(paymentMethodSelect);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonFinish() {
		try {
			OrderManager.finishOrder(order);
			updateTable(null);
			Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
			dialogoInfo.setTitle("Sucesso!");
			dialogoInfo.setHeaderText("Pedido finalizado");
			dialogoInfo.showAndWait();
			getStage().close();
			THSFrontUtils.updateOrder();
		} catch (ManagersExceptions e) {
			Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
			dialogoInfo.setTitle("Erro!");
			dialogoInfo.setHeaderText(e.getExcepetionMessage());
			dialogoInfo.showAndWait();
		}
	}
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(OrderManager.orderIsClose(order)){
				return;
			}
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					PaymentMethod paymentMethod = table.getSelectionModel().getSelectedItem();
					if(paymentMethod != null){
						ScreenPaymentMethodModal screen = new ScreenPaymentMethodModal();
						screen.setController(this);
						screen.setOrder(order);
						screen.setPayment(payment);
						screen.setInstallment(installment);
						screen.setNewPaymentMethod(false);
						screen.setPaymentMethod(paymentMethodSelect);
						screen.start(new Stage());
					}
				}else{
					paymentMethodSelect = table.getSelectionModel().getSelectedItem();
					disableButtons(false);
				}
	        }
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void disableButtons(Boolean disable){
		buttonEdit.setDisable(disable);
		buttonDelete.setDisable(disable);
	}
	
	@Override
	public void updateTable() {
		updateTable(null);
	}
	
	public void updateTable(List<PaymentMethod> paymentsMethod){
		try {
			if(paymentsMethod== null){
				paymentsMethod = PaymentManager.getPaymentMethodsByOrder(order);
			}
			table.setItems(FXCollections.observableArrayList(paymentsMethod));
			if("closed".equals(order.getStatus())){//se o pedido eh fechado nao pode se fazer mais nenhuma acao nele
				buttonNew.setDisable(true);
				buttonFinishOrder.setDisable(true);
				buttonDelete.setDisable(true);
				buttonEdit.setDisable(true);
				labelValuePayed.setText(OrderManager.getValuePriceAsString(order.getAmount()));
			}else{
				if(payment != null){
					labelValuePayed.setText(OrderManager.getValuePriceAsString(payment.getAmountReceived()));
					if(payment.getAmount().compareTo(payment.getAmountReceived()) == 0){
						buttonNew.setDisable(true);
						buttonFinishOrder.setDisable(false);
					}else{
						buttonNew.setDisable(false);
						buttonFinishOrder.setDisable(true);
					}
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			paymentMethods = PaymentManager.getPaymentMethodsByOrder(order);
			columnOne.setCellValueFactory(new PropertyValueFactory<>("id"));
//			columnTwo.setCellValueFactory(new PropertyValueFactory<>("type"));
			columnTwo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PaymentMethod,String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<PaymentMethod, String> paymentMethod) {
					return new SimpleStringProperty(PaymentManager.getDescriptionPaymentMethod(paymentMethod.getValue().getType()));
				}
			});
			columnThree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PaymentMethod,String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<PaymentMethod, String> paymentMethod) {
					return new SimpleStringProperty(OrderManager.getValuePriceAsString(paymentMethod.getValue().getAmount()));
				}
			});
			updateTable(paymentMethods);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void messageSucess(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setTitle("Sucesso");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
		updateTable(null);
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
		if(order != null){
			labelValue.setText(OrderManager.getAmountAsString(order));
			labelTitle.setText("Pagamentos do pedido "+order.getId());
			payment = PaymentManager.getPaymentByOrder(order);
			List<Installment> installments = PaymentManager.getInstallmentsByOrder(order);
			if(installments != null && !installments.isEmpty()){
				installment = installments.get(0);
			}
		}
	}

	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

}
