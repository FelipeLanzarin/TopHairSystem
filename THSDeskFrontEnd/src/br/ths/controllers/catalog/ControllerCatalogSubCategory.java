package br.ths.controllers.catalog;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Category;
import br.ths.beans.SubCategory;
import br.ths.beans.manager.SubCategoryManager;
import br.ths.screens.branch.catalog.ScreenCatalogCategory;
import br.ths.screens.branch.catalog.ScreenCatalogProduct;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerCatalogSubCategory extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<SubCategory> table;
	@FXML private TableColumn<SubCategory, String> columnOne;
	@FXML private TableColumn<SubCategory, String> columnTwo;
	@FXML private TableColumn<SubCategory, String> columnThree;
	@FXML private Button buttonAllProducts;
	@FXML private Label labelTitle;
	
	private List<SubCategory> list;
	private Stage stage;
	private Category category;
	
	public void clickButtonAllProducts(){
		try {
			ScreenCatalogProduct screen = new ScreenCatalogProduct();
			screen.setCategory(category);
			screen.setShowAllProducts(true);
			screen.start(stage);
			
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					SubCategory subCategory = table.getSelectionModel().getSelectedItem();
					if(subCategory != null){
						ScreenCatalogProduct screen = new ScreenCatalogProduct();
						screen.setSubCategory(subCategory);
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
			List<SubCategory> listCategorysSelects = new ArrayList<>();
			for (SubCategory category : list) {
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
	
	public void updateTable(List<SubCategory> listSubCategorys){
		try {
			if(listSubCategorys== null){
				listSubCategorys = SubCategoryManager.getSubCategoriesByCategoryId(category);
			}
			table.setItems(FXCollections.observableArrayList(listSubCategorys));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = SubCategoryManager.getSubCategoriesByCategoryId(category);
			columnOne.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("description"));
			updateTable(list);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void comeBack(){
		try {
			ScreenCatalogCategory screen = new ScreenCatalogCategory();
			screen.start(stage);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public List<SubCategory> getList() {
		return list;
	}

	public void setList(List<SubCategory> list) {
		this.list = list;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
		if(category != null){
			labelTitle.setText(labelTitle.getText()+" "+ category.getName());
		}
	}
	
	
}
