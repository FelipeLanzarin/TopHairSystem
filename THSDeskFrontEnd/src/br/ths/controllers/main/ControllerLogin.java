package br.ths.controllers.main;

import java.net.URL;
import java.util.ResourceBundle;

import br.ths.exceptions.ManagersExceptions;
import br.ths.screens.main.ManagerInitBD;
import br.ths.tools.log.LogTools;
import br.ths.utils.THSFrontUtils;
import fx.tools.controller.GenericController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ControllerLogin extends GenericController{
	
	private static final String STYLE_ERROR = "-fx-border-color:red; -fx-border-radius:4";
	@FXML private TextField textUser;
	@FXML private PasswordField textPassword;
	@FXML private ImageView gif;
	@FXML private Label labelConect;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textUser.setText("admin");
		textPassword.setText("C0nnect123");
	}
	public void login() {
		if(validateFiedls()){
			gif.setVisible(true);
			labelConect.setVisible(true);
			ManagerInitBD dt = new ManagerInitBD();
			dt.setLogin(this);// envia a propria classe para que a thread possa atualizar as informacoes
			Thread thread = new Thread(dt);
			thread.start();
		}
	}
	
	private Boolean validateFiedls(){
		Boolean valid = true;
		try {
			if(textUser.getText().isEmpty()){
				textUser.setStyle(STYLE_ERROR);
				valid = false;
			}
			if(textPassword.getText().isEmpty()){
				textPassword.setStyle(STYLE_ERROR);
				valid = false;
			}
		}catch (Exception e) {
			LogTools.logError(e);
		}
		return valid;
		
	}
	public void sucessConectBD(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					THSFrontUtils.login(textUser.getText(), textPassword.getText());
				} catch (ManagersExceptions e) {
					gif.setVisible(false);
					labelConect.setVisible(false);
					Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
					dialogoInfo.setTitle("Erro!");
					dialogoInfo.setHeaderText("Erro ao fazer login!");
					dialogoInfo.setContentText(e.getExcepetionMessage());
					dialogoInfo.showAndWait();
				}
			}
		});
	}
	
	public void failedConectBD(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
				dialogoInfo.setTitle("Erro!");
				dialogoInfo.setHeaderText("Erro ao Conectar com o Banco de dados");
				dialogoInfo.setContentText("Verifique a sua conexão com a internet!");
				dialogoInfo.showAndWait();
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
	@FXML private void keyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
        }
	}
	public void actionUser(){
		textUser.setStyle("");
	}
	
	public void actionPassword(){
		textPassword.setStyle("");
	}
}
