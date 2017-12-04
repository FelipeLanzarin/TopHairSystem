package br.ths.controllers.catalog.subcategory;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Category;
import br.ths.beans.SubCategory;
import br.ths.beans.manager.CategoryManager;
import br.ths.beans.manager.SubCategoryManager;
import br.ths.screens.catalog.subcategory.ScreenSubCategoryModal;
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

public class ControllerSubCategoryRelationManager extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<SubCategory> table;
	@FXML private TableColumn<SubCategory, String> columnOne;
	@FXML private TableColumn<SubCategory, String> columnTwo;
	@FXML private TableColumn<SubCategory, String> columnThree;
	@FXML private TableColumn<SubCategory, String> columnFour;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	
	private List<SubCategory> list;
	
	public void clickButtonNew(){
		try{
			List<Category> listCategories = CategoryManager.getCategories();
			if(listCategories == null || listCategories.isEmpty()){
				Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
				dialogoInfo.setTitle("Aviso!");
				dialogoInfo.setHeaderText("Nenhuma categoria cadastrada!");
				dialogoInfo.setContentText("Cadastre uma Categoria se você dejesa cadastrar uma SubCategoria. Todas as SubCategorias devem obrigatóriamente ter uma Categoria");
				dialogoInfo.showAndWait();
				return;	
			}
			ScreenSubCategoryModal scream = new ScreenSubCategoryModal();
			scream.setNewSubCategory(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			SubCategory subSubCategory = table.getSelectionModel().getSelectedItem();
			if(subSubCategory == null){
				return;
			}
			ScreenSubCategoryModal scream = new ScreenSubCategoryModal();
			scream.setNewSubCategory(false);
			scream.setRelation(this);
			scream.setSubCategory(subSubCategory);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try {
			SubCategory subSubCategory = table.getSelectionModel().getSelectedItem();
			if(subSubCategory == null){
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir a Categoria "+ subSubCategory.getName()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				if(SubCategoryManager.delete(subSubCategory)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Categoria excluído com sucesso");
					dialog.showAndWait();
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("SubCategoria já possui um produto!");
					dialog.setContentText("Essa SubCategoria já possui um produto. Por esse motivo a mesma não pode ser excluída");
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			SubCategory subSubCategory = table.getSelectionModel().getSelectedItem();
			if(subSubCategory != null){
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
			String subCategoryName = textNameFilter.getText().toLowerCase();
			List<SubCategory> listSubCategorysSelects = new ArrayList<>();
			for (SubCategory subSubCategory : list) {
				String name = subSubCategory.getName().toLowerCase();
				if(name.contains(subCategoryName)){
					listSubCategorysSelects.add(subSubCategory);
				}
			}
			updateTable(listSubCategorysSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<SubCategory> listSubCategorys){
		try {
			if(listSubCategorys== null){
				listSubCategorys = SubCategoryManager.getSubCategories();
			}
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listSubCategorys));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = SubCategoryManager.getSubCategories();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("description"));
			columnFour.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
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

	public List<SubCategory> getList() {
		return list;
	}

	public void setList(List<SubCategory> list) {
		this.list = list;
	}

}
