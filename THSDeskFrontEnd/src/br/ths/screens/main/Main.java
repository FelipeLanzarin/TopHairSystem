package br.ths.screens.main;
	
import java.io.IOException;
import java.net.URL;

import br.ths.controllers.main.ControllerMain;
import br.ths.database.EntityManagerUtil;
import br.ths.tools.THSTools;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	
	private Stage stageMain;
	
	@Override
	public void start(Stage stage) {
		try {
			EntityManagerUtil.initEntityManager();
			THSTools.setConfSession();
			URL arquivoFXML = getClass().getResource(XmlPathUtils.MAIN);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});
			ControllerMain controller = (ControllerMain) loader.getController();
			controller.setStage(stage);
			THSFrontUtils.setControllerMain(controller);
			stage.show();
			stageMain = stage;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void sucessConectBD(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					URL arquivoFXML = getClass().getResource(XmlPathUtils.MAIN);
					FXMLLoader loader = new FXMLLoader(arquivoFXML);
					Parent fxmlParent;
					fxmlParent = (Parent) loader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(fxmlParent));
					stage.setResizable(false);
					stage.show();
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						public void handle(WindowEvent t) {
//							EntityManagerUtil.finalizefinalize();
							Platform.exit();
							System.exit(0);
						}
					});
					stageMain.close();
				} catch (IOException e) {
					LogTools.logError(e);
				}
			}
		});
	}
}
