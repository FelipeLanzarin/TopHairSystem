package br.ths.controllers.profile;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import br.ths.beans.Profile;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskTelephone;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
	
	private static final String STYLE_TEXT_RED_COLOR = "-fx-text-inner-color:red;";
	private static final DecimalFormat df = new DecimalFormat("###,###,##0.00");
	
	public Profile profile;
	
	
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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
		if(profile != null){
			populateTextFields(profile);
		}
	}
	
	public void newOrder(){
		
	}
	
	public void openOrders(){
		
	}
}
