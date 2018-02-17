package br.ths.utils;

import br.ths.beans.Order;
import br.ths.controllers.main.ControllerMain;
import br.ths.controllers.order.ControllerOrderModal;
import br.ths.screens.branch.catalog.ScreenCatalogCategory;
import br.ths.screens.branch.catalog.ScreenCatalogSubCategory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class THSFrontUtils {

	private static ScreenCatalogCategory screenCatalogCategory;
	private static ScreenCatalogSubCategory screenCatalogSubCategory;
	
	private static Order orderSession;
	private static ControllerOrderModal controllerOrderModalSession;
	private static ControllerMain controllerMain;
	
	private static Scene sceneCatalogCategory;
	private static Scene sceneCatalogSubCategory;
	
	private static String pathPicture;
	private static Pane paneMouseClicked;

	public static ScreenCatalogCategory getScreenCatalogCategory() {
		return screenCatalogCategory;
	}

	public static void setScreenCatalogCategory(ScreenCatalogCategory screenCatalogCategory) {
		THSFrontUtils.screenCatalogCategory = screenCatalogCategory;
	}

	public static ScreenCatalogSubCategory getScreenCatalogSubCategory() {
		return screenCatalogSubCategory;
	}

	public static void setScreenCatalogSubCategory(ScreenCatalogSubCategory screenCatalogSubCategory) {
		THSFrontUtils.screenCatalogSubCategory = screenCatalogSubCategory;
	}

	public static Scene getSceneCatalogCategory() {
		return sceneCatalogCategory;
	}

	public static void setSceneCatalogCategory(Scene sceneCatalogCategory) {
		THSFrontUtils.sceneCatalogCategory = sceneCatalogCategory;
	}

	public static Scene getSceneCatalogSubCategory() {
		return sceneCatalogSubCategory;
	}

	public static void setSceneCatalogSubCategory(Scene sceneCatalogSubCategory) {
		THSFrontUtils.sceneCatalogSubCategory = sceneCatalogSubCategory;
	}

	public static Order getOrderSession() {
		return orderSession;
	}

	public static void setOrderSession(Order orderSession) {
		THSFrontUtils.orderSession = orderSession;
	}

	public static ControllerOrderModal getControllerOrderModalSession() {
		return controllerOrderModalSession;
	}

	public static void setControllerOrderModalSession(ControllerOrderModal controllerOrderModalSession) {
		THSFrontUtils.controllerOrderModalSession = controllerOrderModalSession;
	}

	public static ControllerMain getControllerMain() {
		return controllerMain;
	}

	public static void setControllerMain(ControllerMain controllerMain) {
		THSFrontUtils.controllerMain = controllerMain;
	}

	public static String getPathPicture() {
		if(pathPicture == null){
			//get on DB
		}
		return pathPicture;
	}

	public static void setPathPicture(String pathPicture) {
		THSFrontUtils.pathPicture = pathPicture;
	}

	public static Pane getPaneMouseClicked() {
		return paneMouseClicked;
	}

	public static void setPaneMouseClicked(Pane paneMouseClicked) {
		THSFrontUtils.paneMouseClicked = paneMouseClicked;
	}
	
}
