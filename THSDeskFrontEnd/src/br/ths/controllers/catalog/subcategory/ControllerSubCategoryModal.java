package br.ths.controllers.catalog.subcategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.Category;
import br.ths.beans.SubCategory;
import br.ths.beans.manager.CategoryManager;
import br.ths.beans.manager.SubCategoryManager;
import br.ths.tools.log.LogTools;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControllerSubCategoryModal implements Initializable{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";
	private static final String SEP = " - ";
	private static final String KEY_CODE_DOWN = "DOWN";
	private static final String KEY_CODE_UP = "UP";
	private static final String KEY_CODE_LEFT = "LEFT";
	private static final String KEY_CODE_RIGHT = "RIGHT";

	@FXML private Label labelTitle;
	@FXML private TextField textName;
	@FXML private TextArea textDescription;
	@FXML private ComboBox<String> comboCategory;
	
	private List<Category> listCategory;
	private List<Category> listCategoryFilters;
	private Category categorySelected;
	private Stage stage;
	private Boolean newSubCategory;
	private SubCategory subCategory;
	private List<String> states;
	private ControllerSubCategoryRelationManager relation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

    @FXML
	public void filterItens(KeyEvent key){
		try {
			if(KEY_CODE_DOWN.equals(key.getCode().toString()) 
			|| KEY_CODE_UP.equals(key.getCode().toString()) 
			|| KEY_CODE_LEFT.equals(key.getCode().toString())
			|| KEY_CODE_RIGHT.equals(key.getCode().toString())){
				return;
			}
			String categoryName =  (String) comboCategory.getEditor().getText().toLowerCase();
			String categoryNames[] = categoryName.split(SEP);
			if(categoryNames.length > 1){
				categoryName = categoryNames[1];
			}
			listCategoryFilters = new ArrayList<>();
			comboCategory.getItems().clear();
			for (Category category : listCategory) {
				String name = category.getName().toLowerCase();
				if(name.contains(categoryName)){
					listCategoryFilters.add(category);
					comboCategory.getItems().add(category.getId()+ SEP + category.getName());
					if(name.equals(categoryName)){
						categorySelected = category;
						comboCategory.getSelectionModel().select(category.getId()+ SEP + category.getName());
						break;
					}else{
						categorySelected = null;
					}
				}
			}
			comboCategory.show();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(subCategory == null){
				subCategory = new SubCategory();
			}
			subCategory.setName(textName.getText());
			subCategory.setDescription(textDescription.getText());
			subCategory.setCategory(categorySelected);// ja vem preenchido pelo validate
			if(newSubCategory){
				if(SubCategoryManager.create(subCategory)){
					stage.close();
					relation.messageSucess("Categoria cadastrada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar Categoria!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(SubCategoryManager.update(subCategory)){
					stage.close();
					relation.messageSucess("Categoria alterada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao alterar Categoria!");
					dialogoInfo.showAndWait();
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private Category getCategory(String name){
		if(name == null || listCategoryFilters == null){
			return null;
		}
		for (Category category : listCategoryFilters) {
			String categoryName = category.getId() + SEP + category.getName();
			if(name.equals(categoryName)){
				return category;
			}
		}
		return null;
	}
	
	private Boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textName.getText().isEmpty()){
				textName.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(categorySelected == null){
				categorySelected = getCategory(comboCategory.getSelectionModel().getSelectedItem());
				if(categorySelected == null){
					comboCategory.setStyle(STYLE_ERROR);
					valid = false;
				}
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
		
	}
	
	private void populateSubCategory(SubCategory subCategory){
		try {
			textName.setText(subCategory.getName());
			textDescription.setText(subCategory.getDescription());
			if(subCategory.getCategory() != null){	
				comboCategory.setValue(subCategory.getCategory().getId()+ SEP +subCategory.getCategory().getName());
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void actionName(){
		textName.setStyle("");
	}
	public void actionComboCategory(){
		comboCategory.setStyle("");
	}
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Boolean getNewSubCategory() {
		return newSubCategory;
	}

	public void setNewSubCategory(Boolean newSubCategory) {
		this.newSubCategory = newSubCategory;
		if(newSubCategory){
			labelTitle.setText("Cadastrar "+ labelTitle.getText());
		}else{
			labelTitle.setText("Alterar "+ labelTitle.getText());
		}
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
		if(subCategory != null){
			populateSubCategory(subCategory);
		}
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public ControllerSubCategoryRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerSubCategoryRelationManager relation) {
		this.relation = relation;
		listCategory = CategoryManager.getCategories();
		listCategoryFilters = listCategory;
		for (Category category : listCategory) {
			comboCategory.getItems().add(category.getId()+SEP+ category.getName());
		}
	}
	
}
