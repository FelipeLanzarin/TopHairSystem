package br.ths.controllers.order;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import br.ths.beans.CommerceItem;
import br.ths.beans.Employee;
import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.beans.manager.OrderManager;
import br.ths.screens.city.ScreenCityRelation;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskMoney;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


//TODO verificar o que acontece se abrir duas orders ao mesmo tempo
public class ControllerOrderModal extends GenericController{
	
	@FXML private Button buttonDeleteOrder;
	@FXML private Button buttonFinishOrder;
	@FXML private Button buttonEditCi;
	@FXML private Button buttonDeleteCi;
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
			}else{
				Date now = new Date();
				Instant instant = Instant.ofEpochMilli(now.getTime());
			    LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
			    textSchedulable.setValue(localDate);
			    textHour.setText(sdfHour.format(now));
			    textMin.setText(sdfMin.format(now));
			}
			labelTitle.setText("Pedido "+ order.getId());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	
	public void actionName(){
		textName.setStyle("");
	}
	
	public void openCities(){
		try{
			ScreenCityRelation cityRelation = new ScreenCityRelation();
			cityRelation.setLastController(this);
			cityRelation.start(new Stage());
		}catch (Exception e) {
			e.printStackTrace();
			LogTools.logError("Erro ao abrir tela cidades = " + e);
		}
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
		
	}
	
	public void updateProfile(){
		
	}
	
	public void updateEmployee(){
		
	}
	
	public void deleteOrder(){
		
	}
	
	public void finishOrder(){
		
	}
	
	public void clickButtonDelete() {

	}
	
	public void clickButtonEdit() {

	}
	
	public void clickTable(){
		
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
		}
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
