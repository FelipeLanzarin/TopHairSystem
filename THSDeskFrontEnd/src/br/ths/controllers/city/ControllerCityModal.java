package br.ths.controllers.city;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.City;
import br.ths.beans.manager.CityManager;
import br.ths.tools.log.LogTools;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerCityModal implements Initializable{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";

	@FXML private Label labelTitle;
	@FXML private TextField textName;
	@FXML private TextField textCountry;
	@FXML private ComboBox<String> comboStates;
	
	private Stage stage;
	private Boolean newCity;
	private City city;
	private List<String> states;
	private ControllerCityRelationManager relation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(city == null){
				city = new City();
			}
			city.setName(textName.getText());
			city.setUf(comboStates.getSelectionModel().getSelectedItem());
			city.setCountry(textCountry.getText());
			if(newCity){
				if(CityManager.create(city)){
					stage.close();
					relation.messageSucess("Cidade cadastrada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar Cidade!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(CityManager.update(city)){
					stage.close();
					relation.messageSucess("Cidade alterada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar Cidade!");
					dialogoInfo.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private Boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textName.getText().isEmpty()){
				textName.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textCountry.getText().isEmpty()){
				textCountry.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
		
	}
	
	private void populateCity(City city){
		try {
			textName.setText(city.getName());
			textCountry.setText(city.getCountry());
			comboStates.setValue(getState(city.getUf()));
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private String getState(String stateCity){
		try {
			for (String string : states) {
				if(stateCity.equalsIgnoreCase(string)){
					return string;
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return "";
	}
	
	public void actionName(){
		textName.setStyle("");
	}
	
	public void actionCountry(){
		textCountry.setStyle("");
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Boolean getNewCity() {
		return newCity;
	}

	public void setNewCity(Boolean newCity) {
		this.newCity = newCity;
		if(newCity){
			labelTitle.setText("Cadastrar "+ labelTitle.getText());
		}else{
			labelTitle.setText("Alterar "+ labelTitle.getText());
		}
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
		if(city != null){
			populateCity(city);
		}
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public ControllerCityRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerCityRelationManager relation) {
		this.relation = relation;
		states = new ArrayList<>();
		states.add("Rio Grande do Sul - RS");
		states.add("Sem estado");
		states.add("Acre - AC");
		states.add("Alagoas - AL");
		states.add("Amapá - AP");
		states.add("Amazonas - AM");
		states.add("Bahia - BA");
		states.add("Ceará - CE");
		states.add("Distrito Federal - DF");
		states.add("Espírito Santo - ES");
		states.add("Goiás - GO");
		states.add("Maranhão - MA");
		states.add("Mato Grosso - MT");
		states.add("Mato Grosso do Sul - MS");
		states.add("Minas Gerais - MG");
		states.add("Pará - PA");
		states.add("Paraíba - PB");
		states.add("Paraná - PR");
		states.add("Pernambuco - PE");
		states.add("Piauí - PI");
		states.add("Rio de Janeiro - RJ");
		states.add("Rio Grande do Norte - RN");
		states.add("Rondônia - RO");
		states.add("Roraima - RR");
		states.add("Santa Catarina - SC");
		states.add("São Paulo - SP");
		states.add("Sergipe - SE");
		states.add("Tocantins - TO");
		
		comboStates.getItems().addAll(states);
		comboStates.getSelectionModel().select("Rio Grande do Sul - RS");
	}
	
}
