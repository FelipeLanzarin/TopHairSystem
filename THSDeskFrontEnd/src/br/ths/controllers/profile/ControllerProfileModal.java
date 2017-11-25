package br.ths.controllers.profile;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import br.ths.beans.City;
import br.ths.beans.Profile;
import br.ths.beans.manager.ProfileManager;
import br.ths.screens.city.ScreenCityRelation;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskTelephone;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerProfileModal extends GenericController{
	
	@FXML private Label labelTitle;
	@FXML private TextField textName;
	@FXML private TextField textEmail;
	@FXML private MaskTextField textCpf;
	@FXML private MaskTelephone textTelephone;
	@FXML private TextField textAddress;
	@FXML private TextField textNumber;
	@FXML private TextField textNeighborhood;
	@FXML private TextField textCep;
	@FXML private TextField textCity;
	@FXML private ColorPicker color;
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";
	
	public Boolean newProfile;
	public Profile profile;
	public ControllerProfileRelationManager relation;
	public City city;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textCpf.setGenericController(this);
		textTelephone.setGenericController(this);
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
			if(profile == null){
				profile = new Profile();
			}
			
			profile.setName(textName.getText());
			profile.setEmail(textEmail.getText());
			profile.setCpf(textCpf.getText());
			profile.setTelephone(textTelephone.getText());
			profile.setAddress(textAddress.getText());
			profile.setNumber(textNumber.getText());
			profile.setNeighborhood(textNeighborhood.getText());
			profile.setCep(textCep.getText());
			profile.setCity(city);
			if(newProfile){
				profile.setCreationDate(new Date());
				if(ProfileManager.create(profile)){
					getStage().close();
					relation.messageSucess("Cliente cadastrado com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar cliente!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(ProfileManager.update(profile)){
					getStage().close();
					relation.messageSucess("Cliente alterado com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar cliente!");
					dialogoInfo.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
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
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@Override
	public void selectCity(Object obj) {
		try {
			if(obj instanceof City){
				this.city = (City) obj;
				textCity.setText(city.getName());
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public Boolean getNewProfile() {
		return newProfile;
	}

	public void setNewProfile(Boolean newProfile) {
		this.newProfile = newProfile;
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

	public ControllerProfileRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerProfileRelationManager relation) {
		this.relation = relation;
	}
}
