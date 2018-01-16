package br.ths.controllers.order.image;

import java.io.File;
import java.util.List;

import br.ths.beans.Image;
import br.ths.beans.Order;
import br.ths.beans.manager.ImageManager;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;
import fx.tools.controller.GenericController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ControllerImageRelationManager extends GenericController{
	
	@FXML private Button buttonDeleteBefore;
	@FXML private Button buttonDeleteAfter;
	@FXML private Label labelTitle;
	@FXML private AnchorPane anchorPane;
	
	private Image selectedImageBefore;
	private Image selectedImageAfter;
	private List<Image> list;
	private Order order;
	
	public void clickButtonNewBefore(){
		try{
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonNewAfter(){
		try{
			
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDeleteBefore(){
		try {
			Image image = selectedImageBefore;
			if(image == null){
				return;
			}
			deleteImage(image);
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonDeleteAfter(){
		try {
			Image image = selectedImageAfter;
			if(image == null){
				return;
			}
			deleteImage(image);
		} catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void deleteImage(Image image){
		final ButtonType btnSim = new ButtonType("Sim");
		final ButtonType btnNao = new ButtonType("Não");
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Você deseja excluir essa imagem?", btnSim, btnNao);
		alert.showAndWait();

		if (alert.getResult() == btnSim) {
			try{
				if(ImageManager.delete(image)){
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setTitle("Sucesso!");
					dialog.setHeaderText("Imagem excluída com sucesso");
					dialog.showAndWait();
					updateTable(null);
				}else{
					Alert dialog = new Alert(Alert.AlertType.ERROR);
					dialog.setTitle("Erro!");
					dialog.setHeaderText("Erro inesperado!");
					dialog.showAndWait();
				}
			}catch (ManagersExceptions me) {
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setTitle("Erro!");
				dialog.setHeaderText("Erro ao excluir!");
				dialog.setContentText(me.getExcepetionMessage());
				dialog.showAndWait();
			}
		}
	}
	
	
	private void disableButtons(Boolean disable){
		buttonDeleteAfter.setDisable(disable);
		buttonDeleteBefore.setDisable(disable);
	}

	
	public void updateTable(List<Image> listImages){
		try {
			if(listImages== null){
				listImages = ImageManager.getImages(order);
			}
			disableButtons(true);
//			table.setItems(FXCollections.observableArrayList(listImages));
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			list = ImageManager.getImages(order);
			createImageBefore();
			updateTable(list);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void createImageBefore(){
//		ImageView imageView = new ImageView();
//		String path = System.getProperty("user.dir") + "/src/front/ths/images/IMG_0039.JPG";
//		System.out.println(path);
//		File file = new File(path);
//		javafx.scene.image.Image img = new javafx.scene.image.Image(file);
//		imageView.setImage(img);
//		imageView.setLayoutX(-18);
//		imageView.setLayoutY(63);
//		imageView.setRotate(90);
//		anchorPane.getChildren().add(imageView);
	}
	
	public void messageSucess(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setTitle("Sucesso");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
		updateTable(null);
	}

	public List<Image> getList() {
		return list;
	}

	public void setList(List<Image> list) {
		this.list = list;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
		if(order != null){
			labelTitle.setText("Imagens Pedido "+order.getId());
		}
	}
}
