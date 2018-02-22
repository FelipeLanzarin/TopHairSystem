package br.ths.utils;

import br.ths.beans.Order;
import br.ths.beans.UserBranch;
import br.ths.beans.manager.UserBranchManager;
import br.ths.controllers.main.ControllerMain;
import br.ths.controllers.order.ControllerOrderModal;
import br.ths.controllers.order.ControllerOrderRelation;
import br.ths.database.EntityManagerUtil;
import br.ths.exceptions.ManagersExceptions;
import br.ths.screens.branch.catalog.ScreenCatalogCategory;
import br.ths.screens.branch.catalog.ScreenCatalogSubCategory;
import br.ths.screens.main.Main;
import br.ths.tools.THSTools;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class THSFrontUtils {

	private static ScreenCatalogCategory screenCatalogCategory;
	private static ScreenCatalogSubCategory screenCatalogSubCategory;
	private static Main main;
	
	private static Order orderSession;
	private static ControllerOrderModal controllerOrderModalSession;
	private static ControllerOrderRelation controllerOrderRelationSession;
	private static ControllerMain controllerMain;
	
	private static Scene sceneCatalogCategory;
	private static Scene sceneCatalogSubCategory;
	
	private static String pathPicture;
	private static Pane paneMouseClicked;
	
	public static Boolean login(String user, String password) throws ManagersExceptions{
		EntityManagerUtil.initEntityManager();
		UserBranch userBranch = UserBranchManager.getUserBranch(user);
		if(userBranch != null){
			String encryptyPassword = UserBranchManager.getPasswordEncrypted(password);
			if(userBranch.getPassword().equals(encryptyPassword)){
				THSTools.setConfSession(userBranch);
				getMain().openMain();
				return true;
			}
		}
		ManagersExceptions me = new ManagersExceptions();
		me.setExcepetionMessage("Usuário ou senha incorretos");
		throw me;
	}
	
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

	public static ControllerOrderRelation getControllerOrderRelationSession() {
		return controllerOrderRelationSession;
	}

	public static void setControllerOrderRelationSession(ControllerOrderRelation controllerOrderRelationSession) {
		THSFrontUtils.controllerOrderRelationSession = controllerOrderRelationSession;
	}
	
	public static void updateOrder(){
		getControllerMain().updateTable();
		if(getControllerOrderRelationSession() != null){
			getControllerOrderRelationSession().updateTable();
		}
	}

	public static Main getMain() {
		return main;
	}

	public static void setMain(Main main) {
		THSFrontUtils.main = main;
	}
	
}
