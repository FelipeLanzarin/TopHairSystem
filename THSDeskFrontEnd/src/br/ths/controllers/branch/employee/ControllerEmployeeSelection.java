package br.ths.controllers.branch.employee;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Employee;
import br.ths.beans.manager.EmployeeManager;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerEmployeeSelection extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Employee> table;
	@FXML private TableColumn<Employee, String> columnOne;
	@FXML private TableColumn<Employee, String> columnTwo;
	@FXML private Label itemSelected;
	
	private List<Employee> list;
	private GenericController controller;
	private Employee employeeSelected;
	private Stage stage;
	
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					Employee employee = table.getSelectionModel().getSelectedItem();
					if(employee != null){
						stage.close();
						controller.selectEmployeeToOrder(employee);
					}
				}else{
					Employee employee = table.getSelectionModel().getSelectedItem();
					if(employee != null){
						itemSelected.setText(employee.getName());
						employeeSelected = employee;
					}
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
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
			itemSelected.setText("Nenhum Cliente Selecionado");
			employeeSelected = null;
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<Employee> listEmployees){
		try {
			if(listEmployees== null){
				listEmployees = EmployeeManager.getEmployeesActives();
			}
			table.setItems(FXCollections.observableArrayList(listEmployees));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = EmployeeManager.getEmployeesActives();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
			updateTable(list);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public List<Employee> getList() {
		return list;
	}

	public void setList(List<Employee> list) {
		this.list = list;
	}

	public GenericController getController() {
		return controller;
	}

	public void setController(GenericController controller) {
		this.controller = controller;
	}

	public Employee getEmployeeSelected() {
		return employeeSelected;
	}

	public void setEmployeeSelected(Employee employeeSelected) {
		this.employeeSelected = employeeSelected;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
}
