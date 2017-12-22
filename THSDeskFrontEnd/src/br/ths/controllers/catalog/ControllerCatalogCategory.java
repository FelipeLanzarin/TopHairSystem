package br.ths.controllers.catalog;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Category;
import br.ths.beans.manager.CategoryManager;
import br.ths.screens.branch.catalog.ScreenCatalogProduct;
import br.ths.screens.branch.catalog.ScreenCatalogSubCategory;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerCatalogCategory extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Category> table;
	@FXML private TableColumn<Category, String> columnOne;
	@FXML private TableColumn<Category, String> columnTwo;
	@FXML private TableColumn<Category, String> columnThree;
	@FXML private Button buttonAllProducts;
	
	private List<Category> list;
	private Stage stage;
	
	public void clickButtonAllProducts(){
		try {
			ScreenCatalogProduct screenCatalogProduct = new ScreenCatalogProduct();
			screenCatalogProduct.setShowAllProducts(true);
			screenCatalogProduct.start(stage);
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					Category category = table.getSelectionModel().getSelectedItem();
					if(category != null){
						ScreenCatalogSubCategory screen = new ScreenCatalogSubCategory();
						screen.setCategory(category);
						screen.start(stage);
					}
		        }
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
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
	
	public List<Category> getList() {
		return list;
	}

	public void setList(List<Category> list) {
		this.list = list;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
