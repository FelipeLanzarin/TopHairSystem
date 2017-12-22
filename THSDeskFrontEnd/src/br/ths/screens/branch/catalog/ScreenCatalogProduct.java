package br.ths.screens.branch.catalog;

import java.net.URL;

import br.ths.beans.Category;
import br.ths.beans.SubCategory;
import br.ths.controllers.catalog.ControllerCatalogProduct;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCatalogProduct extends Application {
	
	private SubCategory subCategory;
	private Boolean showAllProducts = false;
	private Category category;
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.CATALOG_PRODUCT);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			ControllerCatalogProduct control = (ControllerCatalogProduct) loader.getController();
			control.setStage(stage);
			control.setSubCategory(subCategory);
			control.setCategory(category);
			if(showAllProducts){
				control.setShowAllProducts(showAllProducts);
			}
			control.createTable();
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
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
	}
	
}
