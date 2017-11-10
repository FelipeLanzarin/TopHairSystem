package br.ths.screens.profile;

import java.net.URL;

import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreeanProfileRelationManager extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			//TODO implementar conexão com o banco
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PROFILE_MANAGER);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Top Hair System");
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

}
