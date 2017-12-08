package br.ths.controllers.catalog.category;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Category;
import br.ths.beans.manager.CategoryManager;
import br.ths.exceptions.ManagersExceptions;
import br.ths.screens.catalog.category.ScreenCategoryModal;
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

public class ControllerCategoryRelationManager extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Category> table;
	@FXML private TableColumn<Category, String> columnOne;
	@FXML private TableColumn<Category, String> columnTwo;
	@FXML private TableColumn<Category, String> columnThree;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	
	private List<Category> list;
	
	public void clickButtonNew(){
		try{
			ScreenCategoryModal scream = new ScreenCategoryModal();
			scream.setNewCategory(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			Category category = table.getSelectionModel().getSelectedItem();
			if(category == null){
				return;
			}
			ScreenCategoryModal scream = new ScreenCategoryModal();
			scream.setNewCategory(false);
			scream.setRelation(this);
			scream.setCategory(category);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try {
			Category category = table.getSelectionModel().getSelectedItem();
			if(category == null){
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir a Categoria "+ category.getName()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				try{
					if(CategoryManager.delete(category)){
						Alert dialog = new Alert(Alert.AlertType.INFORMATION);
						dialog.setTitle("Sucesso!");
						dialog.setHeaderText("SubCategoria excluído com sucesso");
						dialog.showAndWait();
						updateTable(null);
					}else{
						Alert dialog = new Alert(Alert.AlertType.ERROR);
						dialog.setTitle("Erro!");
						dialog.setHeaderText("Erro inesperado!");
						dialog.showAndWait();
					}
				}catch (ManagersExceptions me) {
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro ao excluir!");
					dialog.setContentText(me.getExcepetionMessage());
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			Category category = table.getSelectionModel().getSelectedItem();
			if(category != null){
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
			String categoryName = textNameFilter.getText().toLowerCase();
			List<Category> listCategorysSelects = new ArrayList<>();
			for (Category category : list) {
				String name = category.getName().toLowerCase();
				if(name.contains(categoryName)){
					listCategorysSelects.add(category);
				}
			}
			updateTable(listCategorysSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<Category> listCategorys){
		try {
			if(listCategorys== null){
				listCategorys = CategoryManager.getCategories();
			}
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listCategorys));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = CategoryManager.getCategories();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("description"));
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

	public List<Category> getList() {
		return list;
	}

	public void setList(List<Category> list) {
		this.list = list;
	}

}
