package br.ths.utils.beans;

import br.ths.beans.Employee;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EmployeeManagerRown extends Employee{
	
	private static String IMAGE_PENCIL	= "/front/ths/images/manager/pencil.png";
	private static String IMAGE_LIXEIRA	= "/front/ths/images/manager/lixeira.png";
	private static String BUTTON_DELETE_STYLE = "-fx-background-color: #d9534f; "
											  + "-fx-border-color: #d43f3a; "
											  + "-fx-background-radius: 3; "
											  + "-fx-border-radius:3;";
	private static String BUTTON_EDIT_STYLE = "-fx-background-color: #fff; "
											+ "-fx-border-color: #8c8c8c; "
											+ "-fx-background-radius: 3; "
											+ "-fx-border-radius:3;";
	
	private Button edit;
	private Button delete;
	
	public EmployeeManagerRown(){
		initButtonEdit();
		initButtonDelete();
	}
	
	private void initButtonEdit(){
		Image image = new Image(IMAGE_PENCIL);
		ImageView img = new ImageView();
		img.setImage(image);
		edit= new Button(null, img);
		edit.setLayoutX(30.0);
		edit.setLayoutY(6.0);
		edit.setPrefHeight(25.0);
		edit.setMinHeight(25.0);
		edit.setPrefWidth(25.0);
		edit.setMinWidth(25.0);
		edit.setTranslateX(4);
		edit.setStyle(BUTTON_EDIT_STYLE);
	}
	
	private void initButtonDelete(){
		Image image = new Image(IMAGE_LIXEIRA);
		ImageView img = new ImageView();
		img.setImage(image);
		delete= new Button(null, img);
		delete.setLayoutX(30.0);
		delete.setLayoutY(6.0);
		delete.setPrefHeight(25.0);
		delete.setMinHeight(25.0);
		delete.setPrefWidth(25.0);
		delete.setMinWidth(25.0);
		delete.setTranslateX(4);
		delete.setStyle(BUTTON_DELETE_STYLE);
	}

	public Button getEdit() {
		return edit;
	}

	public void setEdit(Button edit) {
		this.edit = edit;
	}

	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}
	
}
