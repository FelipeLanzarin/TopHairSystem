package br.ths.utils.beans;

import br.ths.beans.City;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CityRown extends City{
	
	private static String IMAGE_SELECT	= "/front/ths/images/manager/tick-inside-circle16.png";
	private static String BUTTON_SELECT_STYLE = "-fx-background-color: #fff; "
											+ "-fx-border-color: #8c8c8c; "
											+ "-fx-background-radius: 3; "
											+ "-fx-border-radius:3;";
	
	
	private Button select;
//	private City city;
	
	public CityRown(){
		initButtonSelect();
	}
	
	private void initButtonSelect(){
		Image image = new Image(IMAGE_SELECT);
		ImageView img = new ImageView();
		img.setImage(image);
		select=  new Button(null, img);
		select.setLayoutX(30.0);
		select.setLayoutY(6.0);
		select.setPrefHeight(25.0);
		select.setMinHeight(25.0);
		select.setPrefWidth(25.0);
		select.setMinWidth(25.0);
		select.setStyle(BUTTON_SELECT_STYLE);
	}

	public Button getSelect() {
		return select;
	}

	public void setSelect(Button select) {
		this.select = select;
	}

//	public City getCity() {
//		return city;
//	}
//
//	public void setCity(City city) {
//		this.city = city;
//	}
}
