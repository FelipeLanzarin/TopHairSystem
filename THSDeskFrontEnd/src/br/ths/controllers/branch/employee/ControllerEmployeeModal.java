package br.ths.controllers.branch.employee;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import br.ths.beans.Employee;
import br.ths.beans.manager.EmployeeManager;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerEmployeeModal extends GenericController{
	
	
	@FXML private Label labelTitle;
	@FXML private TextField textName;
	@FXML private TextField textEmail;
	@FXML private MaskTextField textCpf;
	@FXML private TextField textTelephone;
	@FXML private TextField textAddress;
	@FXML private TextField textNumber;
	@FXML private TextField textNeighborhood;
	@FXML private TextField textCep;
	@FXML private TextField textCity;
	@FXML private ColorPicker color;
	
	public Boolean newEmployee;
	public Employee employee;
	public ControllerEmployeeRelationManager relation;
	public Stage stage;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textCpf.setGenericController(this);
	}
	
	public void actionName(){
	}
	
	public void openCities(){
		
	}
	
	public boolean validateFiedls(){
		return true;
	}
	
	//metodo chamado no botao salvar
	public void save(){
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
//		employee.setCity(textCity.getText());
		employee.setColor("#"+ Integer.toHexString(color.getValue().hashCode()));
		System.out.println(employee.getColor());
		if(newEmployee){
			employee.setCreationDate(new Date());
			if(EmployeeManager.create(employee)){
//				stage.close();
				relation.messageSucess("Funcionário cadastrada com sucesso!");
			}else{
				Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
				dialogoInfo.setTitle("Erro!");
				dialogoInfo.setHeaderText("Erro ao cadastrar funcionário!");
				dialogoInfo.showAndWait();
			}
		}else{
			if(EmployeeManager.update(employee)){
				stage.close();
				relation.messageSucess("Funcionário alterado com sucesso!");
			}else{
				Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
				dialogoInfo.setTitle("Erro!");
				dialogoInfo.setHeaderText("Erro ao alterar funcionário!");
				dialogoInfo.showAndWait();
			}
		}
	}
	
	public Boolean getNewEmployee() {
		return newEmployee;
	}

	public void setNewEmployee(Boolean newEmployee) {
		this.newEmployee = newEmployee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public ControllerEmployeeRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerEmployeeRelationManager relation) {
		this.relation = relation;
	}
}
