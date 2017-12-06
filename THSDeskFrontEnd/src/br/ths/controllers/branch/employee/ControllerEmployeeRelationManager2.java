package br.ths.controllers.branch.employee;

import java.util.ArrayList;
import java.util.List;

import br.ths.screens.branch.employee.ScreenEmployeeModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.beans.EmployeeManagerRown;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerEmployeeRelationManager2 extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<EmployeeManagerRown> table;
	@FXML private TableColumn<EmployeeManagerRown, String> columnOne;
	@FXML private TableColumn<EmployeeManagerRown, String> columnTwo;
	@FXML private TableColumn<EmployeeManagerRown, String> columnThree;
	@FXML private TableColumn<EmployeeManagerRown, String> columnFour;
	@FXML private TableColumn<EmployeeManagerRown, String> columnFive;
	@FXML private TableColumn<EmployeeManagerRown, String> columnSix;
	@FXML private TableColumn<EmployeeManagerRown, String> columnSeven;
	
	private List<EmployeeManagerRown> list;
	
	public void clickButtonNew(){
		try{
			ScreenEmployeeModal scream = new ScreenEmployeeModal();
			scream.setNewEmployee(true);
//			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void filterByName(){
		String employeeName = textNameFilter.getText().toLowerCase();
		List<EmployeeManagerRown> listEmployeesSelects = new ArrayList<>();
		for (EmployeeManagerRown employee : list) {
			String name = employee.getName().toLowerCase();
			if(name.contains(employeeName)){
				listEmployeesSelects.add(employee);
			}
		}
		updateTable(listEmployeesSelects);
	}
	
	public void updateTable(List<EmployeeManagerRown> listEmployees){
		if(listEmployees== null){
//			listEmployees = TableViewUtils.getEmployeeManagers(getStage().getScene(), this);
		}
		table.setItems(FXCollections.observableArrayList(listEmployees));

	}
	
	public void createTable(){
//		list = TableViewUtils.getEmployeeManagers(getStage().getScene(), this);
		columnOne.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnTwo.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		columnThree.setCellValueFactory(new PropertyValueFactory<>("email"));
		columnFour.setCellValueFactory(new PropertyValueFactory<>("telephone"));
		columnFive.setCellValueFactory(new PropertyValueFactory<>("address"));
		columnSix.setCellValueFactory(new PropertyValueFactory<>("edit"));
		columnSeven.setCellValueFactory(new PropertyValueFactory<>("delete"));
		updateTable(list);
	}
	
	public void messageSucess(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setTitle("Sucesso");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
		updateTable(null);
	}

	public List<EmployeeManagerRown> getList() {
		return list;
	}

	public void setList(List<EmployeeManagerRown> list) {
		this.list = list;
	}
	
}
