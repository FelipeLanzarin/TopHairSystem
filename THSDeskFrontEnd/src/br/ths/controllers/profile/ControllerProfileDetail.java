package br.ths.controllers.profile;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.beans.manager.OrderManager;
import br.ths.screens.order.ScreenOrderModal;
import br.ths.screens.profile.ScreenOrderProfileRelationManager;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskTelephone;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerProfileDetail extends GenericController{
	
	@FXML private Label labelTitle;
	@FXML private TextField textName;
	@FXML private TextField textEmail;
	@FXML private MaskTextField textCpf;
	@FXML private MaskTelephone textTelephone;
	@FXML private TextField textAddress;
	@FXML private TextField textNumber;
	@FXML private TextField textNeighborhood;
	@FXML private MaskTextField textCep;
	@FXML private TextField textCity;
	@FXML private ColorPicker color;
	@FXML private TextField textBalance;
	@FXML private Button buttonOrders;
	@FXML private Button buttonNewOrder;
	
	private static final DecimalFormat df = new DecimalFormat("###,###,##0.00");
	
	private Profile profile;
	private Boolean onlySee;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textCpf.setGenericController(this);
		textTelephone.setGenericController(this);
		textCep.setGenericController(this);
	}
	
	private void populateTextFields(Profile profile){
		try {
			if(profile == null){
				return;
			}
			textName.setText(profile.getName());
			textEmail.setText(profile.getEmail());
			textCpf.setText(profile.getCpf());
			textTelephone.setText(profile.getTelephone());
			textAddress.setText(profile.getAddress());
			textNumber.setText(profile.getNumber());
			textNeighborhood.setText(profile.getNeighborhood());
			textCep.setText(profile.getCep());
			if(profile.getCity() != null){
				textCity.setText(profile.getCity().getName());
			}
			textBalance.setText(df.format(0.00d));
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}

	
	public void newOrder(){
		try{
			Order order = OrderManager.createForProfile(profile);
			ScreenOrderModal screen = new ScreenOrderModal();
			screen.setOrder(order);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openOrders(){
		try{
			ScreenOrderProfileRelationManager screen = new ScreenOrderProfileRelationManager();
			screen.setProfile(profile);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
		if(profile != null){
			populateTextFields(profile);
		}
	}

	public Boolean getOnlySee() {
		return onlySee;
	}

	public void setOnlySee(Boolean onlySee) {
		this.onlySee = onlySee;
		if (onlySee != null && onlySee) {
			buttonNewOrder.setVisible(false);
			buttonOrders.setVisible(false);
		}
	}
	
}
