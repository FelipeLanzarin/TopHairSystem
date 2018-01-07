package br.ths.controllers.order;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.CommerceItem;
import br.ths.beans.Employee;
import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.beans.manager.CommerceItemManager;
import br.ths.beans.manager.OrderManager;
import br.ths.screens.branch.catalog.ScreenCatalogCategory;
import br.ths.screens.order.commerceitem.ScreenCommerceItemModal;
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
import javafx.stage.Stage;
import javafx.util.Callback;


//TODO verificar o que acontece se abrir duas orders ao mesmo tempo
public class ControllerOrderModal extends GenericController{
	
	@FXML private Button buttonDeleteOrder;
	@FXML private Button buttonFinishOrder;
	@FXML private Button buttonEditCi;
	@FXML private Button buttonDeleteCi;
	@FXML private Button buttonNew;
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
					return new SimpleStringProperty(CommerceItemManager.getFinalAmountAsString(param.getValue()));
				}
			});
			updateTable(list);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<CommerceItem> list){
		try {
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
			}
			if(order.getEmployee() != null){
				textEmployee.setText(order.getEmployee().getName());
			}
			textStatus.setText(OrderManager.getStatusAsString(order));
			textDescriptions.setText(order.getDescription());
			textSubTotal.setText(OrderManager.getAmountAsString(order));
			textDiscounts.setText(OrderManager.getDiscountAsString(order));
			textAmountFinal.setText(OrderManager.getFinalAmountAsString(order));
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
	
	public boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textName.getText().isEmpty()){
				textName.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
	}
	
	//metodo chamado no botao salvar
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			OrderManager.update(order);
//		}catch (ManagersExceptions me) {
//			Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
//			dialogoInfo.setTitle("Erro!");
//			dialogoInfo.setHeaderText("Erro ao executar operação!");
//			dialogoInfo.setContentText(me.getExcepetionMessage());
//			dialogoInfo.showAndWait();
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
	
	public void setAtendedt(){
		try{
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateProfile(){
		try{
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateEmployee(){
		try{
			
		}catch (Exception e) {
			LogTools.logError(e);
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
						createTable();
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
			CommerceItem commerceItem = table.getSelectionModel().getSelectedItem();
			if(commerceItem != null){
				disableButtons(false);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void manageButtons(){
		if(!"open".equals(order.getStatus())){
			buttonDeleteOrder.setDisable(true);
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
}
