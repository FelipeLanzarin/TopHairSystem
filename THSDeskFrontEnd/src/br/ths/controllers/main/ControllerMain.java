package br.ths.controllers.main;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.beans.manager.OrderManager;
import br.ths.screens.branch.catalog.ScreenCatalogCategory;
import br.ths.screens.branch.catalog.product.ScreenProductRelationManager;
import br.ths.screens.branch.employee.ScreenEmployeeRelationManager;
import br.ths.screens.catalog.category.ScreenCategoryRelationManager;
import br.ths.screens.catalog.subcategory.ScreenSubCategoryRelationManager;
import br.ths.screens.city.ScreenCityRelationManager;
import br.ths.screens.order.ScreenOrderModal;
import br.ths.screens.order.ScreenOrderRelation;
import br.ths.screens.profile.ScreenProfileRelation;
import br.ths.screens.profile.ScreenProfileRelationManager;
import br.ths.screens.profile.ScreenProfileSelection;
import br.ths.screens.user.ScreenUserRelationManager;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import fx.tools.controller.GenericController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ControllerMain extends GenericController{

	private static final String STYLE_COLOR = "-fx-border-color:";
	private static final String STYLE_BORER = "-fx-border-radius:4;";
	private static final int INIT = 5;
	@FXML private DatePicker textDate;
	@FXML private AnchorPane anchorPaneLeft;
	@FXML private AnchorPane anchorPaneCenter;
	@FXML private AnchorPane anchorPaneRight;
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		date = calendar.getTime();    
		populateScheduler(date);
	}
	
	
	
	private void populateScheduler(Date date){
		Instant instant = Instant.ofEpochMilli(date.getTime());
	    LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	    textDate.setValue(localDate);
	    int count = 0;
	    Map<String,Pane> map = generateMap();
	    cleanPanes();
	    String lastHour = "00";
	    for (Order order : OrderManager.getOrdersByDate(date)) {
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH");
			String hour = sdf.format(order.getScheduler());
			if(!hour.equals(lastHour)){
				count = 0;
				lastHour=hour;
			}
			createPaneSchedule(order, count, map.get(hour));
			count++;
	    } 	
	}
	
	private void createPaneSchedule(Order order, int count, Pane parentPane){
		Pane pane = new Pane();
		ControllerMain cm = this; 
		pane.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				ScreenOrderModal screen = new ScreenOrderModal();
				screen.setLastController(cm);
				screen.setOrder(order);
				screen.start(new Stage());
			}
		});
		
		pane.setOnMouseEntered(new EventHandler<Event>() {
				public void handle(Event event) {
					if(getStage().getScene() != null){
						getStage().getScene() .setCursor(Cursor.HAND);
					}else{
						System.out.println("scene null");
					}
				}
		});
		
		pane.setOnMouseExited(new EventHandler<Event>() {
			public void handle(Event event) {
				if(getStage().getScene()  != null){
					getStage().getScene() .setCursor(Cursor.DEFAULT);
				}else{
					System.out.println("scene null");
				}
			}
		});
		
		String style = STYLE_COLOR+order.getEmployee().getColor()+";"+STYLE_BORER;
		pane.setStyle(style);
		pane.setPrefWidth(100);
		pane.setPrefHeight(45);
		pane.setLayoutX(INIT+(count*100));
		pane.setLayoutY(19);
		Label scheduler = new Label();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String hour = sdf.format(order.getScheduler());
		scheduler.setText(hour);
		scheduler.setLayoutX(6);
		scheduler.setLayoutY(5);
		scheduler.setFont(getFont());
		pane.getChildren().add(scheduler);
		Label name = new Label();
		name.setText(order.getProfile().getName());
		name.setLayoutX(6);
		name.setLayoutY(24);
		name.setFont(getFont());
		pane.getChildren().add(name);
		parentPane.getChildren().add(pane);
		int weight = (count * 100)+110;
		AnchorPane parent = (AnchorPane)parentPane.getParent();
		if(weight>parent.getPrefWidth()){//ajusta tamanho do painel
			parent.setPrefWidth(weight);
			for (Node node : parent.getChildren()) {
				if(node.getId().equals("line")){//os elementos que tem o ID line sao linhas na agenda
					Pane linePane = (Pane) node;
					linePane.setPrefWidth(weight-10);
				}
			}
		}
		
	}
	
	
	
	private Font getFont(){
		Font f = new Font("SansSerif", 12);
		return f;
	}
	
	private Font getFontBold(){
		Font f = new Font("SansSerif Bold", 14);
		return f;
	}
	
	/**
	 * Metodo para atualizar a agenda de acordo com a data que está selecionada
	 */
	public void selectDate(){
		if(textDate.getValue() != null){
			LocalDate ld = textDate.getValue();
			Calendar c =  Calendar.getInstance();
			c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
			populateScheduler(c.getTime());
		}
	}
	
	public void clickNext(){
		if(textDate.getValue() != null){
			LocalDate ld = textDate.getValue();
			Calendar c =  Calendar.getInstance();
			c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
			c.add(Calendar.DAY_OF_MONTH, 1);
			populateScheduler(c.getTime());
		}
	}
	
	public void clickPreview(){
		if(textDate.getValue() != null){
			LocalDate ld = textDate.getValue();
			Calendar c =  Calendar.getInstance();
			c.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth());
			c.add(Calendar.DAY_OF_MONTH, -1);
			populateScheduler(c.getTime());
		}
	}
	
	public void openEmployeeManager(){
		try{
			ScreenEmployeeRelationManager screen = new ScreenEmployeeRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openCityManager(){
		try{
			ScreenCityRelationManager screen = new ScreenCityRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openCategoryManager(){
		try{
			ScreenCategoryRelationManager screen = new ScreenCategoryRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openProfileManager(){
		try{
			ScreenProfileRelationManager screen = new ScreenProfileRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openSubCategoryManager(){
		try{
			ScreenSubCategoryRelationManager screen = new ScreenSubCategoryRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openProductManager(){
		try{
			ScreenProductRelationManager screen = new ScreenProductRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	public void openUserRelationManager(){
		try{
			ScreenUserRelationManager screen = new ScreenUserRelationManager();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
		
	public void openProfileRelation(){
		try{
			ScreenProfileRelation screen = new ScreenProfileRelation();
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openCatalog(){
		try{
			ScreenCatalogCategory screen = new ScreenCatalogCategory();
			THSFrontUtils.setOrderSession(null);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openLookOrders(){
		try{
			ScreenOrderRelation screen = new ScreenOrderRelation();
			THSFrontUtils.setOrderSession(null);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	public void openNewOrder(){
		try{
			ScreenProfileSelection screen = new ScreenProfileSelection();
			THSFrontUtils.setOrderSession(null);
			screen.setController(this);
			screen.start(new Stage());
		}catch (Exception e) {
			LogTools.logError(e);
		}
	}
	
	@Override
	public void selectProfileToOrder(Object obj) {
		if(obj instanceof Profile){
			Profile profile = (Profile)obj;
			try{
				Order order = OrderManager.createForProfile(profile);
				ScreenOrderModal screen = new ScreenOrderModal();
				screen.setOrder(order);
				screen.start(new Stage());
			}catch (Exception e) {
				LogTools.logError(e);
			}
		}
	}
	
	private void cleanPanes(){
		paneZero.getChildren().clear();
		paneOne.getChildren().clear();
        paneTwo.getChildren().clear();
        paneThree.getChildren().clear();
        paneFour.getChildren().clear();
        paneFive.getChildren().clear();
        paneSix.getChildren().clear();
        paneSeven.getChildren().clear();
        paneEight.getChildren().clear();
        paneNine.getChildren().clear();
        paneTen.getChildren().clear();
        paneEleven.getChildren().clear();
        paneTwelve.getChildren().clear();
        paneThirteen.getChildren().clear();
        paneFourteen.getChildren().clear();
        paneFifteen.getChildren().clear();
        paneSixteen.getChildren().clear();
        paneSeventeen.getChildren().clear();
        paneEighteen.getChildren().clear();
        paneNineteen.getChildren().clear();
        paneTwenty.getChildren().clear();
        paneTwentyOne.getChildren().clear();
        paneTwentyTwo.getChildren().clear();
        paneTwentyThree.getChildren().clear();
		paneZero.getChildren().add(createTime("00:00"));
		paneOne.getChildren().add(createTime("01:00"));
        paneTwo.getChildren().add(createTime("02:00"));
        paneThree.getChildren().add(createTime("03:00"));
        paneFour.getChildren().add(createTime("04:00"));
        paneFive.getChildren().add(createTime("05:00"));
        paneSix.getChildren().add(createTime("06:00"));
        paneSeven.getChildren().add(createTime("07:00"));
        paneEight.getChildren().add(createTime("08:00"));
        paneNine.getChildren().add(createTime("09:00"));
        paneTen.getChildren().add(createTime("10:00"));
        paneEleven.getChildren().add(createTime("11:00"));
        paneTwelve.getChildren().add(createTime("12:00"));
        paneThirteen.getChildren().add(createTime("13:00"));
        paneFourteen.getChildren().add(createTime("14:00"));
        paneFifteen.getChildren().add(createTime("15:00"));
        paneSixteen.getChildren().add(createTime("16:00"));
        paneSeventeen.getChildren().add(createTime("17:00"));
        paneEighteen.getChildren().add(createTime("18:00"));
        paneNineteen.getChildren().add(createTime("19:00"));
        paneTwenty.getChildren().add(createTime("20:00"));
        paneTwentyOne.getChildren().add(createTime("21:00"));
        paneTwentyTwo.getChildren().add(createTime("22:00"));
        paneTwentyThree.getChildren().add(createTime("23:00"));
        anchorPaneLeft.setPrefWidth(418);
        anchorPaneCenter.setPrefWidth(418);
        anchorPaneRight.setPrefWidth(418);
	}
	
	private Map<String,Pane> generateMap(){
		Map<String, Pane> map = new HashMap<>();
		map.put("00", paneZero);
		map.put("01", paneOne);
		map.put("02", paneTwo);
		map.put("03", paneThree);
		map.put("04", paneFour);
		map.put("05", paneFive);
		map.put("06", paneSix);
		map.put("07", paneSeven);
		map.put("08", paneEight);
		map.put("09", paneNine);
		map.put("10", paneTen);
		map.put("11", paneEleven);
		map.put("12", paneTwelve);
		map.put("13", paneThirteen);
		map.put("14", paneFourteen);
		map.put("15", paneFifteen);
		map.put("16", paneSixteen);
		map.put("17", paneSeventeen);
		map.put("18", paneEighteen);
		map.put("19", paneNineteen);
		map.put("20", paneTwenty);
		map.put("21", paneTwentyOne);
		map.put("22", paneTwentyTwo);
		map.put("23", paneTwentyThree);
		return map;
	}
	
	private Label createTime(String time){
		Label l = new Label();
		l.setFont(getFontBold());
		l.setLayoutX(5);
		l.setText(time);
		return l;
	}
	
	@FXML private Pane paneZero;
	@FXML private Pane paneOne;
	@FXML private Pane paneTwo;
	@FXML private Pane paneThree;
	@FXML private Pane paneFour;
	@FXML private Pane paneFive;
	@FXML private Pane paneSix;
	@FXML private Pane paneSeven;
	@FXML private Pane paneEight;
	@FXML private Pane paneNine;
	@FXML private Pane paneTen;
	@FXML private Pane paneEleven;
	@FXML private Pane paneTwelve;
	@FXML private Pane paneThirteen;
	@FXML private Pane paneFourteen;
	@FXML private Pane paneFifteen;
	@FXML private Pane paneSixteen;
	@FXML private Pane paneSeventeen;
	@FXML private Pane paneEighteen;
	@FXML private Pane paneNineteen;
	@FXML private Pane paneTwenty;
	@FXML private Pane paneTwentyOne;
	@FXML private Pane paneTwentyTwo;
	@FXML private Pane paneTwentyThree;

}
