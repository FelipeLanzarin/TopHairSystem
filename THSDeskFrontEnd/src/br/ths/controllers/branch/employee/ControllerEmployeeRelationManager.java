package br.ths.controllers.branch.employee;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Employee;
import br.ths.beans.manager.EmployeeManager;
import br.ths.screens.branch.employee.ScreenEmployeeModal;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerEmployeeRelationManager extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Employee> table;
	@FXML private TableColumn<Employee, String> columnOne;
	@FXML private TableColumn<Employee, String> columnTwo;
	@FXML private TableColumn<Employee, String> columnThree;
	@FXML private TableColumn<Employee, String> columnFour;
	@FXML private TableColumn<Employee, String> columnFive;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	
	private List<Employee> list;
	
	public void clickButtonNew(){
		try{
			ScreenEmployeeModal scream = new ScreenEmployeeModal();
			scream.setNewEmployee(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			Employee employee = table.getSelectionModel().getSelectedItem();
			if(employee == null){
				return;
			}
			ScreenEmployeeModal scream = new ScreenEmployeeModal();
			scream.setNewEmployee(false);
			scream.setRelation(this);
			scream.setEmployee(employee);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try {
			Employee employee = table.getSelectionModel().getSelectedItem();
			if(employee == null){
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir o funcionário "+ employee.getName()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				if(EmployeeManager.delete(employee)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Funcionário excluído com sucesso");
					dialog.showAndWait();
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro ao excluir Funcionário!");
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			Employee employee = table.getSelectionModel().getSelectedItem();
			if(employee != null){
				disableButtons(false);
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void disableButtons(Boolean disable){
		buttonEdit.setDisable(disable);
		buttonDelete.setDisable(disable);
	}
	
	
	public void filterByName(){
		try {
			String employeeName = textNameFilter.getText().toLowerCase();
			List<Employee> listEmployeesSelects = new ArrayList<>();
			for (Employee employee : list) {
				String name = employee.getName().toLowerCase();
				if(name.contains(employeeName)){
					listEmployeesSelects.add(employee);
				}
			}
			updateTable(listEmployeesSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<Employee> listEmployees){
		try {
			if(listEmployees== null){
				listEmployees = EmployeeManager.getEmployees();
			}
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listEmployees));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = EmployeeManager.getEmployees();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("email"));
			columnFour.setCellValueFactory(new PropertyValueFactory<>("telephone"));
			columnFive.setCellValueFactory(new PropertyValueFactory<>("address"));
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

	public List<Employee> getList() {
		return list;
	}

	public void setList(List<Employee> list) {
		this.list = list;
	}

}
