package br.ths.utils;

import br.ths.beans.Order;
import br.ths.controllers.order.ControllerOrderModal;
import br.ths.screens.branch.catalog.ScreenCatalogCategory;
import br.ths.screens.branch.catalog.ScreenCatalogSubCategory;
import javafx.scene.Scene;

public class THSFrontUtils {

	private static ScreenCatalogCategory screenCatalogCategory;
	private static ScreenCatalogSubCategory screenCatalogSubCategory;
	
	private static Order orderSession;
	private static ControllerOrderModal controllerOrderModalSession;
	
	private static Scene sceneCatalogCategory;
	private static Scene sceneCatalogSubCategory;

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
	
}
