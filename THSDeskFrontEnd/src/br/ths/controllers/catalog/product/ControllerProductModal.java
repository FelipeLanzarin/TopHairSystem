package br.ths.controllers.catalog.product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ths.beans.Category;
import br.ths.beans.Product;
import br.ths.beans.SubCategory;
import br.ths.beans.manager.CategoryManager;
import br.ths.beans.manager.ProductManager;
import br.ths.beans.manager.SubCategoryManager;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import fx.tools.mask.MaskMoney;
import fx.tools.mask.MaskTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControllerProductModal extends GenericController{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";
	private static final String SEP = " - ";
	private static final String KEY_CODE_DOWN = "DOWN";
	private static final String KEY_CODE_UP = "UP";
	private static final String KEY_CODE_LEFT = "LEFT";
	private static final String KEY_CODE_RIGHT = "RIGHT";

	@FXML private Label labelTitle;
	@FXML private ComboBox<String> comboCategory;
	@FXML private ComboBox<String> comboSubCategory;
	@FXML private TextField textName;
	@FXML private ToggleGroup productType;
	@FXML private RadioButton radioService;
	@FXML private RadioButton radioProduct;
	@FXML private ToggleGroup unitType;
	@FXML private RadioButton radioUn;
	@FXML private RadioButton radioMl;
	@FXML private RadioButton radioLt;
	@FXML private MaskMoney textCostPrice;
	@FXML private MaskMoney textPrice;
	@FXML private MaskTextField textUnitValue;
	@FXML private TextArea textDescription;
	
	private List<Category> listCategory;
	private List<Category> listCategoryFilters;
	private List<SubCategory> listSubCategory;
	private List<SubCategory> listSubCategoryFilters;
	private Category categorySelected;
	private SubCategory subCategorySelected;
	private Stage stage;
	private Boolean newProduct;
	private Product product;
	private List<String> states;
	private ControllerProductRelationManager relation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textCostPrice.setGenericController(this);
		textPrice.setGenericController(this);
		textUnitValue.setGenericController(this);
	}

    @FXML
	public void filterItensCategory(KeyEvent key){
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
						populateSubCetegiresByCategory(categorySelected);
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
    
    @FXML
	public void filterItensSubCategory(KeyEvent key){
		try {
			if(KEY_CODE_DOWN.equals(key.getCode().toString()) 
			|| KEY_CODE_UP.equals(key.getCode().toString()) 
			|| KEY_CODE_LEFT.equals(key.getCode().toString())
			|| KEY_CODE_RIGHT.equals(key.getCode().toString())){
				return;
			}
			String subcategoryName =  (String) comboSubCategory.getEditor().getText().toLowerCase();
			String subcategoryNames[] = subcategoryName.split(SEP);
			if(subcategoryNames.length > 1){
				subcategoryName = subcategoryNames[1];
			}
			listSubCategoryFilters = new ArrayList<>();
			comboSubCategory.getItems().clear();
			for (SubCategory subcategory : listSubCategory) {
				String name = subcategory.getName().toLowerCase();
				if(name.contains(subcategoryName)){
					listSubCategoryFilters.add(subcategory);
					comboSubCategory.getItems().add(subcategory.getId()+ SEP + subcategory.getName());
					if(name.equals(subcategoryName)){
						subCategorySelected = subcategory;
						comboSubCategory.getSelectionModel().select(subcategory.getId()+ SEP + subcategory.getName());
						break;
					}else{
						subCategorySelected = null;
					}
				}
			}
			comboSubCategory.show();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void save(){
		try {
			if(!validateFiedls()){
				return;
			}
			if(product == null){
				product = new Product();
			}
			product.setName(textName.getText());
			product.setDescription(textDescription.getText());
			product.setSubCategory(subCategorySelected);// ja vem preenchido pelo validate
			if(newProduct){
				if(ProductManager.create(product)){
					stage.close();
					relation.messageSucess("Categoria cadastrada com sucesso!");
				}else{
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao cadastrar Categoria!");
					dialogoInfo.showAndWait();
				}
			}else{
				if(ProductManager.update(product)){
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
	
	private SubCategory getSubCategory(String name){
		if(name == null || listSubCategoryFilters == null){
			return null;
		}
		for (SubCategory subCategory : listSubCategoryFilters) {
			String categoryName = subCategory.getId() + SEP + subCategory.getName();
			if(name.equals(categoryName)){
				return subCategory;
			}
		}
		return null;
	}
	
	private Boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(categorySelected == null){
				categorySelected = getCategory(comboCategory.getSelectionModel().getSelectedItem());
				if(categorySelected == null){
					comboCategory.setStyle(STYLE_ERROR);
					valid = false;
				}
			}
			if(subCategorySelected == null){
				subCategorySelected = getSubCategory(comboSubCategory.getSelectionModel().getSelectedItem());
				if(categorySelected == null){
					comboCategory.setStyle(STYLE_ERROR);
					valid = false;
				}
			}
			if(textName.getText().isEmpty()){
				textName.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textCostPrice.getText().isEmpty()){
				textCostPrice.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textPrice.getText().isEmpty()){
				textPrice.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textUnitValue.getText().isEmpty()){
				textUnitValue.setStyle(STYLE_ERROR);
				valid = false;
			}
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
		
	}
	
	private void populateProduct(Product product){
		try {
			textName.setText(product.getName());
			textDescription.setText(product.getDescription());
			if(product.getSubCategory() != null){	
				comboCategory.setValue(product.getSubCategory().getId()+ SEP +product.getSubCategory().getName());
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void populateSubcategories(){
		categorySelected = getCategory(comboCategory.getSelectionModel().getSelectedItem());
		if(categorySelected == null){
			return;
		}
		populateSubCetegiresByCategory(categorySelected);
	}
	
	private void populateSubCetegiresByCategory(Category categorySelected){
		subCategorySelected = null;
		if(categorySelected == null){
			return;
		}
		listSubCategory = SubCategoryManager.getSubCategoriesByCategoryId(categorySelected);
		listSubCategoryFilters = listSubCategory;
		comboSubCategory.getItems().clear();
		for (SubCategory subCategory : listSubCategory) {
			comboSubCategory.getItems().add(subCategory.getId()+SEP+ subCategory.getName());
		}
	}
	
	public void actionComboCategory(){
		comboCategory.setStyle("");
	}
	
	public void actionComboSubCategory(){
		comboSubCategory.setStyle("");
	}
	
	public void actionName(){
		textName.setStyle("");
	}
	
	public void actionCostPrice(){
		textCostPrice.setStyle("");
	}
	
	public void actionPrice(){
		textPrice.setStyle("");
	}
	
	public void actionUnitValue(){
		textUnitValue.setStyle("");
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Boolean getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
		if(newProduct){
			labelTitle.setText("Cadastrar "+ labelTitle.getText());
		}else{
			labelTitle.setText("Alterar "+ labelTitle.getText());
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		if(product != null){
			populateProduct(product);
		}
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public ControllerProductRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerProductRelationManager relation) {
		this.relation = relation;
		listCategory = CategoryManager.getCategories();
		listCategoryFilters = listCategory;
		for (Category category : listCategory) {
			comboCategory.getItems().add(category.getId()+SEP+ category.getName());
		}		
	}
	
}
