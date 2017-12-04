package br.ths.controllers.catalog.product;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Product;
import br.ths.beans.manager.ProductManager;
import br.ths.screens.branch.catalog.product.ScreenProductModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.TableViewUtils;
import br.ths.utils.beans.ProductRown;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerProductRelationManager {
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<ProductRown> table;
	@FXML private TableColumn<Product, String> columnOne;
	@FXML private TableColumn<Product, String> columnTwo;
	@FXML private TableColumn<Product, String> columnThree;
	@FXML private TableColumn<Product, String> columnFour;
	@FXML private TableColumn<Product, String> columnFive;
	@FXML private TableColumn<Product, String> columnSix;
	@FXML private Button buttonEdit;
	@FXML private Button buttonDelete;
	
	private List<ProductRown> list;
	
	public void clickButtonNew(){
		try{
			ScreenProductModal scream = new ScreenProductModal();
			scream.setNewProduct(true);
			scream.setRelation(this);
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonEdit(){
		try{
			ProductRown productRown = table.getSelectionModel().getSelectedItem();
			if(productRown == null || productRown.getProduct() == null){
				return;
			}
			ScreenProductModal scream = new ScreenProductModal();
			scream.setNewProduct(false);
			scream.setRelation(this);
			scream.setProdutc(productRown.getProduct());
			scream.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDelete(){
		try {
			Product product = table.getSelectionModel().getSelectedItem();
			if(product == null){
				return;
			}
			final ButtonType btnSim = new ButtonType("Sim");
			final ButtonType btnNao = new ButtonType("Não");
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir a SubCategoria "+ product.getName()+"?", btnSim, btnNao);
			alert.showAndWait();

			if (alert.getResult() == btnSim) {
				if(ProductManager.delete(product)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Produto excluído com sucesso");
					dialog.showAndWait();
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Produto já foi vendido uma vez!");
					dialog.setContentText("Essa Produto já foi vendido uma vez. Por esse motivo o mesmo não pode ser excluído");
					dialog.showAndWait();
				}
			}
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickTable(){
		try {
			Product product = table.getSelectionModel().getSelectedItem();
			if(product != null){
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
			String productName = textNameFilter.getText().toLowerCase();
			List<ProductRown> listProductsSelects = new ArrayList<>();
			for (ProductRown product : list) {
				String name = product.getName().toLowerCase();
				if(name.contains(productName)){
					listProductsSelects.add(product);
				}
			}
			updateTable(listProductsSelects);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<ProductRown> listProducts){
		try {
			if(listProducts== null){
				listProducts = TableViewUtils.getProductManagers();
			}
			disableButtons(true);
			table.setItems(FXCollections.observableArrayList(listProducts));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = TableViewUtils.getProductManagers();
			columnOne.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("type"));
			columnFour.setCellValueFactory(new PropertyValueFactory<>("unType"));
			columnFive.setCellValueFactory(new PropertyValueFactory<>("priceFormat"));
			columnSix.setCellValueFactory(new PropertyValueFactory<>("un"));
			
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

	public List<ProductRown> getList() {
		return list;
	}

	public void setList(List<ProductRown> list) {
		this.list = list;
	}

}
