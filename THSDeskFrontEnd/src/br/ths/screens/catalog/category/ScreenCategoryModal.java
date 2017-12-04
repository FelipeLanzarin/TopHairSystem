package br.ths.screens.catalog.category;

import java.net.URL;

import br.ths.beans.Category;
import br.ths.controllers.catalog.category.ControllerCategoryModal;
import br.ths.controllers.catalog.category.ControllerCategoryRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenCategoryModal extends Application {
	
	public Boolean newCategory;
	public ControllerCategoryRelationManager relation;
	public Category category;
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.CATEGORY_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Categoria");
			ControllerCategoryModal controllerCategoryModal = (ControllerCategoryModal) loader.getController();
			controllerCategoryModal.setNewCategory(newCategory);
			controllerCategoryModal.setRelation(relation);
			controllerCategoryModal.setCategory(category);
			controllerCategoryModal.setStage(stage);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Boolean getNewCategory() {
		return newCategory;
	}

	public void setNewCategory(Boolean newCategory) {
		this.newCategory = newCategory;
	}

	public ControllerCategoryRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerCategoryRelationManager relation) {
		this.relation = relation;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
