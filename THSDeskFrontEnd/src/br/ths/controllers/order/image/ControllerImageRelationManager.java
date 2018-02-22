package br.ths.controllers.order.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import br.ths.beans.Image;
import br.ths.beans.Order;
import br.ths.beans.manager.ImageManager;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import fx.tools.controller.GenericController;
import fx.tools.mouse.MouseEnventControler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class ControllerImageRelationManager extends GenericController{
	
	@FXML private Button buttonDeleteBefore;
	@FXML private Button buttonDeleteAfter;
	@FXML private Label labelTitle;
	@FXML private AnchorPane anchorPane;
	
	private Image selectedImageBefore;
	private Image selectedImageAfter;
	private List<Image> listBefore;
	private List<Image> listAfter;
	private Order order;
	private File file;
	
	public void clickButtonNewBefore(){
		try{
			FileChooser fileChooser = new FileChooser();
			if(THSFrontUtils.getPathPicture() != null){
				fileChooser.setInitialDirectory(new File(THSFrontUtils.getPathPicture()));
			}
			file = fileChooser.showOpenDialog(getStage());
			if (file != null){
				THSFrontUtils.setPathPicture(file.getParent());
			}
			createImageBefore();
			createTable();
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void clickButtonNewAfter(){
		try{
			FileChooser fileChooser = new FileChooser();
			if(THSFrontUtils.getPathPicture() != null){
				fileChooser.setInitialDirectory(new File(THSFrontUtils.getPathPicture()));
			}
			file = fileChooser.showOpenDialog(getStage());
			if (file != null){
				THSFrontUtils.setPathPicture(file.getParent());
			}
			createImageAfter();
			createTable();
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
	
	public void showImages(List<Image> listAfter, List<Image> listBefore){
        
		List<ImageView> listIVBefore = new ArrayList<>();
		List<ImageView> listIVAfter = new ArrayList<>();
		List<Pane> panelistIVBefore = new ArrayList<>();
		List<Pane> panelistIVAfter = new ArrayList<>();
		for (Image image : listBefore) {
			Pane pane = new Pane();
			BufferedImage imagem = null;
	        File b = new File(image.getPath());
	        try {
	        	imagem = ImageIO.read(b);
	        } catch (IOException e) {
	        	continue;
	        }
		    ImageView img = new ImageView(b.toURI().toString());
		    resizeImage(img, imagem, pane);
		    
		    ImageMouseClick ic = new ImageMouseClick();
		    ic.setPane(pane);
		    ic.setAfter(false);
		    ic.setController(this);
		    ic.setImage(image);
		    pane.setOnMouseClicked(MouseEnventControler.getMouseCliked(ic));
		    listIVBefore.add(img);
		    pane.getChildren().add(img);
		    panelistIVBefore.add(pane);
		}
		
		for (Image image : listAfter) {
			Pane pane = new Pane();
			BufferedImage imagem = null;
	        File b = new File(image.getPath());
	        try {
	        	imagem = ImageIO.read(b);
	        } catch (IOException e) {
	        	continue;
	        }
		    ImageView img = new ImageView(b.toURI().toString());
		    resizeImage(img, imagem, pane);
		    
		    ImageMouseClick ic = new ImageMouseClick();
		    ic.setPane(pane);
		    ic.setAfter(true);
		    ic.setController(this);
		    ic.setImage(image);
		    pane.setOnMouseClicked(MouseEnventControler.getMouseCliked(ic));
		    pane.getChildren().add(img);
		    
		    listIVAfter.add(img);
		    panelistIVAfter.add(pane);
		    
		}
		Double nextLaoutY = 0.0d;
		int count = 0;
		for (Pane pane : panelistIVBefore) {
			pane.setLayoutY(nextLaoutY);
//			img.setLayoutY(nextLaoutY);
			anchorPane.getChildren().add(pane);
			if(panelistIVAfter.size()>count){
				Pane maior = pane;
				if(panelistIVAfter.get(count).getPrefHeight()> pane.getPrefHeight()){
					maior = panelistIVAfter.get(count);
				}
			   	nextLaoutY+=maior.getPrefHeight()+1;
			}else{
			   	nextLaoutY+=pane.getPrefHeight()+1;
			}
			count++;
		}
		nextLaoutY = 0.0d;
		count = 0;
		for (Pane pane : panelistIVAfter) {
			pane.setLayoutY(nextLaoutY);
			pane.setLayoutX(1230-pane.getPrefWidth());// para alinhar a direita
		    anchorPane.getChildren().add(pane);
		    if(panelistIVBefore.size()>count){
		    	Pane maior = pane;
				if(panelistIVBefore.get(count).getPrefHeight()> pane.getPrefHeight()){
					maior = panelistIVBefore.get(count);
				}
				nextLaoutY+=maior.getPrefHeight()+1;
		    }else{
		    	nextLaoutY+=pane.getPrefHeight()+1;
		    }
		    count++;
		}
	}
	
	/**
	 * Utilizado para redimensionar as imagens
	 * @param img
	 * @param imagem
	 */
	private void resizeImage(ImageView img, BufferedImage imagem, Pane pane){
		double h = imagem.getHeight();
	    double w = imagem.getWidth();
	    boolean wMore = w>h;
		if(w>600 && wMore){//redimensiona pela largura
	    	double diff = (((100 * 600)/w)); //dif de porcentagem entre a imagem real e o tamanho permitido
	    	h = (diff/100)*h;
	    	w = 600;
	    }else if(h>600){//redimensiona pela altura
	    	double diff = (((100 * 600)/h)); //dif de porcentagem entre a imagem real e o tamanho permitido
	    	w = (diff/100)*w;
	    	h=600;
	    }
		 //seta novas dimensoes
	    img.setFitWidth(w-10);
	    img.setFitHeight(h-10);
	    img.setLayoutX(5);
	    img.setLayoutY(5);
	    pane.setPrefHeight(h);
	    pane.setPrefWidth(w);
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
					anchorPane.getChildren().clear();
					updateTable(null, null);
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
	
	
	public void disableButtonBefore(Boolean disable){
		buttonDeleteBefore.setDisable(disable);
	}
	
	public void disableButtonAfter(Boolean disable){
		buttonDeleteAfter.setDisable(disable);
	}
	
	public void updateTable(List<Image> listAfter, List<Image> listBefore){
		try {
			if(listAfter== null){
				listAfter = ImageManager.getImagesAfter(order);
			}
			if(listBefore== null){
				listBefore = ImageManager.getImagesBefore(order);
			}
			disableButtonAfter(true);
			disableButtonBefore(true);
			showImages(listAfter,listBefore);
		}catch (Exception e) {
			LogTools.logError(e);
		}

	}
	
	public void createTable(){
		try {
			anchorPane.getChildren().clear();
			listAfter = ImageManager.getImagesAfter(order);
			listBefore = ImageManager.getImagesBefore(order);
			updateTable(listAfter, listBefore);
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	private void createImageBefore(){
		if(file == null){
			return;
		}
		Image image = new Image();
		image.setOrder(order);
		image.setDescription(file.getName());
		image.setPartOfProcess("before");
		String path = ImageManager.setImagePath(image);
		if(path != null){
			String currentDir = System.getProperty("user.dir")+"/";
			File newFile = new File(path);
			
			if (!newFile.exists()) {
				if (!newFile.mkdirs()) {
					//System.out.println("create folder " + newFile.getAbsolutePath());
					LogTools.logError("Erro ao criar diretorio para relizar backup");
				}
			}
			File fileImage = new File(newFile.getAbsolutePath()+"/"+image.getDescription());
			if(!copyDirectory(file, fileImage)){
				return;
			}
		}
		ImageManager.create(image);
	}
	
	private void createImageAfter(){
		if(file == null){
			return;
		}
		Image image = new Image();
		image.setOrder(order);
		image.setDescription(file.getName());
		image.setPartOfProcess("after");
		String path = ImageManager.setImagePath(image);
		if(path != null){
			String currentDir = System.getProperty("user.dir")+"/";
			File newFile = new File(path);
			
			if (!newFile.exists()) {
				if (!newFile.mkdirs()) {
					LogTools.logError("Erro ao criar diretorio para relizar backup");
				}
			}
			File fileImage = new File(newFile.getAbsolutePath()+"/"+image.getDescription());
			if(!copyDirectory(file, fileImage)){
				return;
			}
		}
		ImageManager.create(image);
	}
	
	public Boolean copyDirectory(File originFile , File destinationFile) {
		try {
			BufferedImage imagem = ImageIO.read(originFile);
			ImageIO.write(imagem, "jpg", destinationFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void messageSucess(String message){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setTitle("Sucesso");
		dialogoInfo.setHeaderText(message);
		dialogoInfo.showAndWait();
		updateTable(null, null);
	}
	
	public void anchorPaneMouseClicked(){
		setSelectedImageAfter(null);
		setSelectedImageBefore(null);
		Pane lastPane = THSFrontUtils.getPaneMouseClicked();
		if(lastPane != null){
			lastPane.setStyle("");
		}
	}

	public List<Image> getListBefore() {
		return listBefore;
	}

	public void setListBefore(List<Image> listBefore) {
		this.listBefore = listBefore;
	}

	public List<Image> getListAfter() {
		return listAfter;
	}

	public void setListAfter(List<Image> listAfter) {
		this.listAfter = listAfter;
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

	public Image getSelectedImageBefore() {
		return selectedImageBefore;
	}

	public void setSelectedImageBefore(Image selectedImageBefore) {
		this.selectedImageBefore = selectedImageBefore;
	}

	public Image getSelectedImageAfter() {
		return selectedImageAfter;
	}

	public void setSelectedImageAfter(Image selectedImageAfter) {
		this.selectedImageAfter = selectedImageAfter;
	}
	
}
