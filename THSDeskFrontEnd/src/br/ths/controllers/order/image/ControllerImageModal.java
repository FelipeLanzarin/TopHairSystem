package br.ths.controllers.order.image;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.Category;
import br.ths.beans.manager.CategoryManager;
import br.ths.tools.log.LogTools;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerImageModal implements Initializable{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";

	@FXML private Label labelTitle;
	@FXML private TextField textName;
	@FXML private TextArea textDescription;
	@FXML private ComboBox<String> comboStates;
	
	private Stage stage;
	private Boolean newCategory;
	private Category category;
	private List<String> states;
	private ControllerImageRelationManager relation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(category == null){
				category = new Category();
			}
			category.setName(textName.getText());
			category.setDescription(textDescription.getText());
			if(newCategory){
				if(CategoryManager.create(category)){
					stage.close();
					relation.messageSucess("SubCategoria cadastrada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar SubCategoria!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(CategoryManager.update(category)){
					stage.close();
					relation.messageSucess("SubCategoria alterada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar SubCategoria!");
					dialogoInfo.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private Boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textName.getText().isEmpty()){
				textName.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
		
	}
	
	private void populateCategory(Category category){
		try {
			textName.setText(category.getName());
			textDescription.setText(category.getDescription());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void actionName(){
		textName.setStyle("");
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Boolean getNewCategory() {
		return newCategory;
	}

	public void setNewCategory(Boolean newCategory) {
		this.newCategory = newCategory;
		if(newCategory){
			labelTitle.setText("Cadastrar "+ labelTitle.getText());
		}else{
			labelTitle.setText("Alterar "+ labelTitle.getText());
		}
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
		if(category != null){
			populateCategory(category);
		}
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public ControllerImageRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerImageRelationManager relation) {
		this.relation = relation;
	}
	
}
