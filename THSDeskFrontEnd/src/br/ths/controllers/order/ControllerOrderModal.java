package br.ths.controllers.order;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.CommerceItem;
import br.ths.beans.Employee;
import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.beans.manager.CommerceItemManager;
import br.ths.beans.manager.OrderManager;
import br.ths.exceptions.ManagersExceptions;
import br.ths.screens.branch.catalog.ScreenCatalogCategory;
import br.ths.screens.branch.employee.ScreenEmployeeSelection;
import br.ths.screens.order.commerceitem.ScreenCommerceItemModal;
import br.ths.screens.profile.ScreenProfileSelection;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskMoney;
import fx.tools.mask.MaskTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;


//TODO verificar o que acontece se abrir duas orders ao mesmo tempo
public class ControllerOrderModal extends GenericController{
	
	@FXML private Button buttonDeleteOrder;
	@FXML private Button buttonFinishOrder;
	@FXML private Button buttonEditCi;
	@FXML private Button buttonDeleteCi;
	@FXML private Button buttonNew;
	@FXML private Button buttonSetAtended;
	@FXML private Button buttonSave;
	@FXML private ImageView imageFindEmployee;
	@FXML private ImageView imageUpdateProfile;
	@FXML private Label labelTitle;
	@FXML private TextField textName;
	@FXML private TextField textEmployee;
	@FXML private TextField textStatus;
	@FXML private TextArea textDescriptions;
	@FXML private MaskMoney textSubTotal;
	@FXML private MaskMoney textDiscounts;
	@FXML private MaskMoney textAmountFinal;
	@FXML private DatePicker textSchedulable;
	@FXML private MaskTextField textHour;
	@FXML private MaskTextField textMin;
	@FXML private TableView<CommerceItem> table;
	@FXML private TableColumn<CommerceItem, String> columnOne;
	@FXML private TableColumn<CommerceItem, String> columnTwo;
	@FXML private TableColumn<CommerceItem, String> columnThree;
	@FXML private TableColumn<CommerceItem, String> columnFour;
	@FXML private TableColumn<CommerceItem, String> columnFive;
	@FXML private TableColumn<CommerceItem, String> columnSix;
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";
	private Profile profile;
	private Employee employee;
	private Order order;
	private Date scheduler;
	private GenericController lastController;
	
	private SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
	private SimpleDateFormat sdfMin = new SimpleDateFormat("mm");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textHour.setGenericController(this);
		textMin.setGenericController(this);
		textAmountFinal.setGenericController(this);
		textDiscounts.setGenericController(this);
		textSubTotal.setGenericController(this);
	}
	public void createTable(){
		try {
			List<CommerceItem> list = CommerceItemManager.getCommerceItemsByOrder(order);
			columnOne.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CommerceItem,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CommerceItem, String> param) {
					return new SimpleStringProperty(CommerceItemManager.getProductId(param.getValue()));
				}
			});
			columnTwo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CommerceItem,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CommerceItem, String> param) {
					return new SimpleStringProperty(CommerceItemManager.getProductName(param.getValue()));
				}
			});
			columnThree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CommerceItem,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CommerceItem, String> param) {
					return new SimpleStringProperty(CommerceItemManager.getUnitPriceAsString(param.getValue()));
				}
			});
			columnFour.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			columnFive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CommerceItem,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CommerceItem, String> param) {
					return new SimpleStringProperty(CommerceItemManager.getDiscountAsString(param.getValue()));
				}
			});
			columnSix.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CommerceItem,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<CommerceItem, String> param) {
					return new SimpleStringProperty(CommerceItemManager.getAmountAsString(param.getValue()));
				}
			});
			updateTable(list, false);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<CommerceItem> list, Boolean populateOrderFields){
		try {
			if(list == null){
				list = CommerceItemManager.getCommerceItemsByOrder(order);
			}
			if(populateOrderFields){
				populateTextFields(OrderManager.recalculateOrder(order));
				if(lastController != null){
					lastController.updateTable();
				}
			}
			if(list!= null){
				table.setItems(FXCollections.observableArrayList(list));
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	private void populateTextFields(Order order){
		try {
			if(order == null){
				return;
			}
			if(order.getProfile() != null){
				textName.setText(order.getProfile().getName());
				profile = order.getProfile();
			}
			if(order.getEmployee() != null){
				textEmployee.setText(order.getEmployee().getName());
				employee = order.getEmployee();
			}
			textStatus.setText(OrderManager.getStatusAsString(order));
			textDescriptions.setText(order.getDescription());
			textSubTotal.setText(OrderManager.getSubTotalAmountAsString(order));
			textDiscounts.setText(OrderManager.getDiscountAsString(order));
			textAmountFinal.setText(OrderManager.getAmountAsString(order));
			if(order.getScheduler() != null){
				Instant instant = Instant.ofEpochMilli(order.getScheduler().getTime());
			    LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
			    textSchedulable.setValue(localDate);
			    textHour.setText(sdfHour.format(order.getScheduler()));
			    textMin.setText(sdfMin.format(order.getScheduler()));
			}
			labelTitle.setText("Pedido "+ order.getId());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	
	public void actionName(){
		textName.setStyle("");
	}
	
	public void actionSchedulable(){
		textSchedulable.setStyle("");
	}
	
	public void actionHour(){
		textHour.setStyle("");
	}
	
	public void actionMin(){
		textMin.setStyle("");
	}
	
	public void actionDiscount(){
		try{
			textDiscounts.setStyle("");
			String discount = textDiscounts.getText();
			if(discount.isEmpty()){
				textAmountFinal.setText(textSubTotal.getText());
				return;
			}else{
				Double discountAmount = OrderManager.getValuePriceAsDouble(discount);
				Double subTotal = 0.0D;
				if(order.getAmount() != null){
					subTotal = order.getSubTotalAmount();
				}
				
				Double finalPrice = OrderManager.roundValue(subTotal - discountAmount);
				if(finalPrice>=0){
					textAmountFinal.setText(OrderManager.getValuePriceAsString(finalPrice));
				}else{
					textAmountFinal.setText("0,00");
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void actionValueFinal(){
		try{
			textAmountFinal.setStyle("");
			String amount = textAmountFinal.getText();
			if(amount.isEmpty()){
				textDiscounts.setText("0,00");
				return;
			}else{
				Double finalPrice = OrderManager.getValuePriceAsDouble(amount);
				Double subTotal = 0.0D;
				if(order.getAmount() != null){
					subTotal = order.getSubTotalAmount();
				}
	
				Double discount = OrderManager.roundValue(subTotal - finalPrice);
				if(discount>=0){
					textDiscounts.setText(OrderManager.getValuePriceAsString(discount));
				}else{
					textDiscounts.setText("0,00");
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textName.getText().isEmpty()){
				textName.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textDiscounts.getText().isEmpty()){
				textDiscounts.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textAmountFinal.getText().isEmpty()){
				textAmountFinal.setStyle(STYLE_ERROR);
				valid = false;
			}
			
			Date date = getDate(textSchedulable);
			String min = textMin.getText();
			String hour = textHour.getText();
			
			if(date!= null || !min.isEmpty() || !hour.isEmpty()){
				if(date == null){
					textSchedulable.setStyle(STYLE_ERROR);
					valid = false;
				}
				
				if(min.isEmpty()){
					textMin.setStyle(STYLE_ERROR);
					valid = false;
				}
				
				if(hour.isEmpty()){
					textHour.setStyle(STYLE_ERROR);
					valid = false;
				}
				if(valid){
					Integer h =Integer.parseInt(textHour.getText());
					Integer m = Integer.parseInt(textMin.getText());
					if(h>24 || h<0){
						textHour.setStyle(STYLE_ERROR);
						valid = false;
					}
					
					if(m<0 || m>59){
						textMin.setStyle(STYLE_ERROR);
						valid = false;
					}
					if(valid){
						setSchedulerDate(date, h, m);
					}
				}
				
			}
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
	}
	
	private void setSchedulerDate(Date date, Integer h, Integer m){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); //colocando o objeto Date no Calendar
		calendar.set(Calendar.HOUR_OF_DAY, h); //zerando as horas, minuots e segundos..
		calendar.set(Calendar.MINUTE, m);
		scheduler = calendar.getTime();
	}
	
	private Date getDate(DatePicker datePicker){
		if(datePicker.getValue() != null){
			LocalDate ld = datePicker.getValue();
			Calendar c =  Calendar.getInstance();
			c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
			return c.getTime();
		}
		return null;
	}
	//metodo chamado no botao salvar
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			order.setDiscount(OrderManager.getValuePriceAsDouble(textDiscounts.getText()));
			order.setAmount(OrderManager.getValuePriceAsDouble(textAmountFinal.getText()));
			order.setDescription(textDescriptions.getText());
			order.setProfile(profile);
			order.setEmployee(employee);
			order.setScheduler(scheduler);
			if(scheduler != null){
				order.setIsAttendance(true);
			}else{
				order.setIsAttendance(false);
			}
			if(OrderManager.update(order)){
				Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
				dialogoInfo.setTitle("Sucesso!");
				dialogoInfo.setHeaderText("O pedido foi salvo com sucesso!");
				dialogoInfo.showAndWait();
				if(lastController != null){
					lastController.updateTable();
				}
			}
		}catch (ManagersExceptions me) {
			Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
			dialogoInfo.setTitle("Erro!");
			dialogoInfo.setHeaderText("Erro salvar pedido!");
			dialogoInfo.setContentText(me.getExcepetionMessage());
			dialogoInfo.showAndWait();
		}catch (Exception e) {
			LogTools.logError(e);
			Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
			dialogoInfo.setTitle("Erro!");
			dialogoInfo.setHeaderText("Erro inesperado!");
			dialogoInfo.showAndWait();
		}
	}
	
	public void hClick() {
		 textHour.requestFocus(); // get focus first
		 textHour.positionCaret(0);
		 textHour.selectNextWord();
	}
	
	public void setAtended(){
		try{
			order.setStatus("attended");
			if(OrderManager.update(order)){
				manageButtons();
				Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
				dialogoInfo.setTitle("Sucesso!");
				dialogoInfo.setHeaderText("O pedido marcado como atendido!");
				dialogoInfo.showAndWait();
				if(lastController != null){
					lastController.updateTable();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateProfile(){
		try{
			ScreenProfileSelection screen = new ScreenProfileSelection();
			screen.setController(this);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@Override
	public void selectProfileToOrder(Object obj) {
		if(obj instanceof Profile){
			profile = (Profile)obj;
			if(profile != null){
				textName.setText(profile.getName());
			}
		}
	}
	
	public void updateEmployee(){
		try{
			ScreenEmployeeSelection screen = new ScreenEmployeeSelection();
			screen.setController(this);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@Override
	public void selectEmployeeToOrder(Object obj) {
		if(obj instanceof Employee){
			employee = (Employee)obj;
			if(employee != null){
				textEmployee.setText(employee.getName());
			}
		}
	}
	public void deleteOrder(){
		try{
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void finishOrder(){
		try{
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete() {
		try{
			try{
				CommerceItem ci = table.getSelectionModel().getSelectedItem();
				if(ci == null){
					return;
				}
				final ButtonType btnSim = new ButtonType("Sim");
				final ButtonType btnNao = new ButtonType("Não");
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja remover o Produto "+ ci.getProduct().getName()+"?", btnSim, btnNao);
				alert.showAndWait();

				if (alert.getResult() == btnSim) {
					if(CommerceItemManager.delete(ci)){
//						Alert dialog = new Alert(Alert.AlertType.INFORMATION);
//						dialog.setTitle("Sucesso!");
//						dialog.setHeaderText("Produto removido com sucesso");
//						dialog.showAndWait();
						updateTable(null,true);
					}else{
						Alert dialog = new Alert(Alert.AlertType.ERROR);
						dialog.setTitle("Erro!");
						dialog.setHeaderText("Erro ao remover produto!");
						dialog.showAndWait();
					}
				}
			}catch (Exception e) {
				LogTools.logError(e);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit() {
		try{
			try{
				CommerceItem ci = table.getSelectionModel().getSelectedItem();
				if(ci == null){
					return;
				}
				ScreenCommerceItemModal screen = new ScreenCommerceItemModal();
				screen.setOrder(order);
				screen.setOrderModal(this);
				screen.setNewCommerceItem(false);
				screen.setCommerceItem(ci);
				screen.setStageCatalog(null);
				screen.start(new Stage());
			}catch (Exception e) {
				LogTools.logError(e);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonNew(){
		try{
			ScreenCatalogCategory screen = new ScreenCatalogCategory();
			THSFrontUtils.setOrderSession(order);
			THSFrontUtils.setControllerOrderModalSession(this);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try{
			if(OrderManager.orderIsOpen(order)){
				CommerceItem commerceItem = table.getSelectionModel().getSelectedItem();
				if(commerceItem != null){
					disableButtons(false);
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openImages(){
		try{
//			ScreenImageRelationManager screen = new ScreenImageRelationManager();
//			screen.setOrder(order);
//			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void manageButtons(){
		if(!OrderManager.orderIsOpen(order)){
			buttonDeleteOrder.setDisable(true);
			buttonDeleteCi.setDisable(true);
			buttonNew.setDisable(true);
			buttonEditCi.setDisable(true);
			buttonSetAtended.setDisable(true);
			buttonSave.setDisable(true);
			textAmountFinal.setEditable(false);
			textDescriptions.setEditable(false);
			textDiscounts.setEditable(false);
			textHour.setEditable(false);
			textMin.setEditable(false);
			textSchedulable.setDisable(true);
			textSchedulable.setStyle("-fx-opacity: 1");
			textSchedulable.getEditor().setStyle("-fx-opacity: 1");
			imageFindEmployee.setDisable(true);
			imageUpdateProfile.setDisable(true);
		}
	}
	
	private void disableButtons(Boolean disable){
		buttonEditCi.setDisable(disable);
		buttonDeleteCi.setDisable(disable);
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
		if(order != null){
			populateTextFields(order);
			manageButtons();
			createTable();
		}
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public GenericController getLastController() {
		return lastController;
	}
	public void setLastController(GenericController lastController) {
		this.lastController = lastController;
	}
}
