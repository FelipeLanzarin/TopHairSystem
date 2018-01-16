package br.ths.controllers.order;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.beans.manager.OrderManager;
import br.ths.screens.order.ScreenOrderModal;
import br.ths.screens.profile.ScreenProfileSelection;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import fx.tools.controller.GenericController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerOrderRelation extends GenericController{
	
	@FXML private Label labelTitle;
	@FXML private TableView<Order> table;
	@FXML private TableColumn<Order, String> columnOne;
	@FXML private TableColumn<Order, String> columnTwo;
	@FXML private TableColumn<Order, String> columnThree;
	@FXML private TableColumn<Order, String> columnFour;
	@FXML private TableColumn<Order, String> columnFive;
	@FXML private TableColumn<Order, String> columnSix;
	@FXML private DatePicker textInitialDate;
	@FXML private DatePicker textFinalDate;
	@FXML private DatePicker textInitialDateAt;
	@FXML private DatePicker textFinalDateAt;
	@FXML private TextField textId;
	@FXML private TextField textName;
	@FXML private ComboBox<String> boxStatus;
	private List<Order> list;
	private Date initialDate;
	private Date finalDate;
	private Date initialDateAt;
	private Date finalDateAt;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boxStatus.getItems().add("Esperando Atendimento");
		boxStatus.getItems().add("Atendido");
		boxStatus.getItems().add("Finalizado");
	}
	
	public void clickButtonNew(){
		try{
			ScreenProfileSelection screen = new ScreenProfileSelection();
			THSFrontUtils.setOrderSession(null);
			screen.setController(this);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@Override
	public void selectProfileToOrder(Object obj) {
		if(obj instanceof Profile){
			Profile profile = (Profile)obj;
			try{
				Order order = OrderManager.createForProfile(profile);
				ScreenOrderModal screen = new ScreenOrderModal();
				screen.setOrder(order);
				screen.setLastController(this);
				screen.start(new Stage());
			}catch (Exception e) {
				LogTools.logError(e);
			}
		}
	}
	
	public void filterById(){
		try{
			String id =  textId.getText();
			List<Order> listFilter = new ArrayList<>();
			for (Order order : list) {
				String orderId = order.getId()+"";
				if(orderId.contains(id)){
					listFilter.add(order);
				}
			}
			updateTable(listFilter);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByName(){
		try{
			String name =  textName.getText();
			List<Order> listFilter = new ArrayList<>();
			for (Order order : list) {
				if(order.getProfile() != null){
					if(order.getProfile().getName().toLowerCase().contains(name.toLowerCase())){
						listFilter.add(order);
					}
				}
			}
			updateTable(listFilter);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByInitialDate(){
		try{
			initialDate = getDate(textInitialDate);
			filterByDate();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByFinalDate(){
		try{
			finalDate = getDate(textFinalDate);
			filterByDate();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByInitialDateAt(){
		try{
			initialDateAt = getDate(textInitialDateAt);
			filterByDateAt();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByFinalDateAt(){
		try{
			finalDateAt = getDate(textFinalDateAt);
			filterByDateAt();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByStatus(){
		try{
			String status = boxStatus.getSelectionModel().getSelectedItem();
			if(status == null){
				return;
			}
			List<Order> listFilter = new ArrayList<>();
			for (Order order : list) {
				String statusOrder = OrderManager.getStatusAsString(order);
				if(statusOrder.equals(status)){
					listFilter.add(order);
				}
			}
			updateTable(listFilter);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void filterByDate(){
		if(initialDate != null && finalDate != null){
			List<Order> listFilter = new ArrayList<>();
			for (Order order : list) {
				if(order.getCreationDate() != null){
					Date orderDate = order.getCreationDate();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String initialDateString = sdf.format(initialDate);
					String finalDateString = sdf.format(finalDate);
					String orderDateString = sdf.format(orderDate);
					Boolean equalsInitial =  initialDateString.equals(orderDateString);
					Boolean equalsFinalDate = finalDateString.equals(orderDateString);
					if((initialDate.before(orderDate)|| equalsInitial) && (finalDate.after(orderDate) ||equalsFinalDate)){
						listFilter.add(order);
					}
				}
			}
			updateTable(listFilter);
		}
	}
	
	private void filterByDateAt(){
		if(initialDateAt != null && finalDateAt != null){
			List<Order> listFilter = new ArrayList<>();
			for (Order order : list) {
				if(order.getScheduler() != null){
					Date orderDate = order.getScheduler();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String initialDateString = sdf.format(initialDateAt);
					String finalDateString = sdf.format(finalDateAt);
					String orderDateString = sdf.format(orderDate);
					Boolean equalsInitial =  initialDateString.equals(orderDateString);
					Boolean equalsFinalDate = finalDateString.equals(orderDateString);
					if((initialDateAt.before(orderDate)|| equalsInitial) && (finalDateAt.after(orderDate) ||equalsFinalDate)){
						listFilter.add(order);
					}
				}
			}
			updateTable(listFilter);
		}
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
	
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					Order order = table.getSelectionModel().getSelectedItem();
					if(order != null){
						ScreenOrderModal orderModal = new ScreenOrderModal();
						orderModal.setLastController(this);
						orderModal.setOrder(order);
						orderModal.start(new Stage());
					}
		        }
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@Override
	public void updateTable() {
		updateTable(null);
	}
	
	public void updateTable(List<Order> listOrder){
		try {
			if(listOrder== null){
				listOrder = OrderManager.getOrders();
			}
			table.setItems(FXCollections.observableArrayList(listOrder));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = OrderManager.getOrders();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnTwo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order,String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<Order, String> order) {
					return new SimpleStringProperty(OrderManager.getCreationDateAsString(order.getValue()));
				}
			});
			columnThree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order,String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<Order, String> order) {
					return new SimpleStringProperty(OrderManager.getProfileAsString(order.getValue()));
				}
			});
			columnFour.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order,String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<Order, String> order) {
					return new SimpleStringProperty(OrderManager.getSchedulerAsString(order.getValue()));
				}
			});
			columnFive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order,String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<Order, String> order) {
					return new SimpleStringProperty(OrderManager.getStatusAsString(order.getValue()));
				}
			});
			columnSix.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order,String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<Order, String> order) {
					return new SimpleStringProperty(OrderManager.getAmountAsString(order.getValue()));
				}
			});
		
			updateTable(list);
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

	public List<Order> getList() {
		return list;
	}

	public void setList(List<Order> list) {
		this.list = list;
	}

}
