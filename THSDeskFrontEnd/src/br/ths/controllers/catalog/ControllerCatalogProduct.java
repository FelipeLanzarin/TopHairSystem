package br.ths.controllers.catalog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.ths.beans.Category;
import br.ths.beans.Product;
import br.ths.beans.SubCategory;
import br.ths.beans.manager.ProductManager;
import br.ths.screens.branch.catalog.product.ScreenProductDetail;
import br.ths.screens.order.commerceitem.ScreenCommerceItemModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import fx.tools.controller.GenericController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerCatalogProduct extends GenericController{
	
	@FXML private TextField textNameFilter;
	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, String> columnOne;
	@FXML private TableColumn<Product, String> columnTwo;
	@FXML private TableColumn<Product, String> columnThree;
	@FXML private TableColumn<Product, String> columnFour;
	@FXML private Button buttonAdd;
	@FXML private Label labelTitle;
	
	private List<Product> list;
	private Stage stage;
	private SubCategory subCategory;
	private Boolean showAllProducts = false;
	private Category category;
	private Product productSelect;
	
	public void clickButtonAdd(){
		try {
			if(productSelect != null){
				if (THSFrontUtils.getOrderSession() == null) {
					Alert dialog = new Alert(Alert.AlertType.WARNING);
					dialog.setTitle("Atenção!");
					dialog.setHeaderText("Nenhum pedido aberto!");
					dialog.setContentText("Abra um pedido para adicionar esse item à compra");
					dialog.showAndWait();
					return;
				}
				ScreenCommerceItemModal screen = new ScreenCommerceItemModal();
				screen.setOrder(THSFrontUtils.getOrderSession());
				screen.setOrderModal(THSFrontUtils.getControllerOrderModalSession());
				screen.setProdutc(productSelect);
				screen.setNewCommerceItem(true);
				screen.setStageCatalog(stage);
				screen.start(new Stage());
			}else{
				Alert dialog = new Alert(Alert.AlertType.WARNING);
				dialog.setTitle("Atenção!");
				dialog.setHeaderText("Selecione um produto!");
				dialog.setContentText("Selecione um produto para adicioná-lo a sua compra");
				dialog.showAndWait();
			}
			
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@FXML
	public void clickTable(MouseEvent mouseEvent){
		try {
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
				if(mouseEvent.getClickCount() == 2){
					Product product = table.getSelectionModel().getSelectedItem();
					if(product != null){
						ScreenProductDetail screen = new ScreenProductDetail();
						screen.setProduct(product);
						screen.setStageCatalog(stage);
						screen.start(new Stage());
					}
		        }else{
		        	Product product = table.getSelectionModel().getSelectedItem();
		        	if(product != null){
		        		buttonAdd.setDisable(false);
		        		productSelect = product;
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
			List<Product> listProducts = new ArrayList<>();
			for (Product product : list) {
				String name = product.getName().toLowerCase();
				if(name.contains(categoryName)){
					listProducts.add(product);
				}
			}
			updateTable(listProducts);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void updateTable(List<Product> listProducts){
		try {
			if(listProducts== null){
				if(showAllProducts){
					if(category == null){
						listProducts = ProductManager.getProducts();
					}else{
						listProducts = ProductManager.getProductsByCategory(category);
					}
				}else{
					listProducts = ProductManager.getProductsBySubCategory(subCategory);
				}
			}
			table.setItems(FXCollections.observableArrayList(listProducts));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			if(showAllProducts){
				if(category == null){
					list = ProductManager.getProducts();
				}else{
					list = ProductManager.getProductsByCategory(category);
				}
			}else{
				list = ProductManager.getProductsBySubCategory(subCategory);
			}
			columnOne.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
			columnThree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Product, String> product) {
					DecimalFormat df = new DecimalFormat("###,###,##0.00");
					String price= "0,00";
					try{
						price = df.format(product.getValue().getPrice());
					}catch (Exception e) {
						e.printStackTrace();
					}
					return new SimpleStringProperty("R$ "+price);
				}
			});
			columnFour.setCellValueFactory(new PropertyValueFactory<>("description"));
			updateTable(list);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void comeBack(){
		try {
			if(showAllProducts && category == null){
				stage.setScene(THSFrontUtils.getSceneCatalogCategory());
			}else{
				stage.setScene(THSFrontUtils.getSceneCatalogSubCategory());
			}
			stage.show();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
		if(subCategory != null){
			labelTitle.setText(labelTitle.getText()+" "+ subCategory.getName());
		}else{
			labelTitle.setText("Catálogo - Todos os produtos");
		}
	}

	public Boolean getShowAllProducts() {
		return showAllProducts;
	}

	public void setShowAllProducts(Boolean showAllProducts) {
		this.showAllProducts = showAllProducts;
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
