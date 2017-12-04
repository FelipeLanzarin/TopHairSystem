package br.ths.screens.catalog.subcategory;

import java.net.URL;

import br.ths.beans.SubCategory;
import br.ths.controllers.catalog.subcategory.ControllerSubCategoryModal;
import br.ths.controllers.catalog.subcategory.ControllerSubCategoryRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenSubCategoryModal extends Application {
	

	public Boolean newSubCategory;
	public ControllerSubCategoryRelationManager relation;
	public SubCategory subCategory;
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.SUBCATEGORY_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("SubCategoria");
			ControllerSubCategoryModal controllerSubCategoryModal = (ControllerSubCategoryModal) loader.getController();
			controllerSubCategoryModal.setNewSubCategory(newSubCategory);
			controllerSubCategoryModal.setRelation(relation);
			controllerSubCategoryModal.setSubCategory(subCategory);
			controllerSubCategoryModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Boolean getNewSubCategory() {
		return newSubCategory;
	}

	public void setNewSubCategory(Boolean newSubCategory) {
		this.newSubCategory = newSubCategory;
	}

	public ControllerSubCategoryRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerSubCategoryRelationManager relation) {
		this.relation = relation;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
}
