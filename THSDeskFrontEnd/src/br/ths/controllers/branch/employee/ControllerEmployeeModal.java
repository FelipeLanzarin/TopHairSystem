package br.ths.controllers.branch.employee;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import br.ths.beans.City;
import br.ths.beans.Employee;
import br.ths.beans.manager.EmployeeManager;
import br.ths.screens.city.ScreenCityRelation;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskTelephone;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControllerEmployeeModal extends GenericController{
	
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
	@FXML private ToggleGroup groupActive;
	@FXML private RadioButton radioButtonYes;
	@FXML private RadioButton radioButtonNo;
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";
	
	public Boolean newEmployee;
	public Employee employee;
	public ControllerEmployeeRelationManager relation;
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
			if(employee == null){
				employee = new Employee();
			}
			
			employee.setName(textName.getText());
			employee.setEmail(textEmail.getText());
			employee.setCpf(textCpf.getText());
			employee.setTelephone(textTelephone.getText());
			employee.setAddress(textAddress.getText());
			employee.setNumber(textNumber.getText());
			employee.setNeighborhood(textNeighborhood.getText());
			employee.setCep(textCep.getText());
			employee.setCity(city);
			employee.setColor("#"+ Integer.toHexString(color.getValue().hashCode()));
			RadioButton radioSelect = (RadioButton) groupActive.getSelectedToggle();
			if("true".equals(radioSelect.getId())){
				employee.setActive(true);
			}else{
				employee.setActive(false);
			}
			if(newEmployee){
				employee.setCreationDate(new Date());
				if(EmployeeManager.create(employee)){
					getStage().close();
					relation.messageSucess("Funcionário cadastrado com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar funcionário!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(EmployeeManager.update(employee)){
					getStage().close();
					relation.messageSucess("Funcionário alterado com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar funcionário!");
					dialogoInfo.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void populateTextFields(Employee employee){
		try {
			if(employee == null){
				return;
			}
			textName.setText(employee.getName());
			textEmail.setText(employee.getEmail());
			textCpf.setText(employee.getCpf());
			textTelephone.setText(employee.getTelephone());
			textAddress.setText(employee.getAddress());
			textNumber.setText(employee.getNumber());
			textNeighborhood.setText(employee.getNeighborhood());
			textCep.setText(employee.getCep());
			if(employee.getCity() != null){
				textCity.setText(employee.getCity().getName());
			}
			color.setValue(Color.web(employee.getColor()));
			if(employee.getActive() != null && !employee.getActive()){
				radioButtonNo.setSelected(true);
			}else{
				radioButtonYes.setSelected(true);
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
	
	public Boolean getNewEmployee() {
		return newEmployee;
	}

	public void setNewEmployee(Boolean newEmployee) {
		this.newEmployee = newEmployee;
		if(newEmployee){
			labelTitle.setText("Cadastrar Funcionário");
		}else{
			labelTitle.setText("Alterar Funcionário");
		}
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		if(employee != null){
			populateTextFields(employee);
		}
	}

	public ControllerEmployeeRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerEmployeeRelationManager relation) {
		this.relation = relation;
	}
}
