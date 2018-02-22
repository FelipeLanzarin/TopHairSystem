package br.ths.screens.cashier;
	
import java.net.URL;

import br.ths.beans.Cashier;
import br.ths.beans.Transaction;
import br.ths.controllers.cashier.ControllerCashierRelationManager;
import br.ths.controllers.cashier.ControllerTransactionModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ScreenTransactionModal extends Application {
	
	private Transaction transaction;
	private Boolean newTransaction;
	private ControllerCashierRelationManager relation;
	private String type;
	private Cashier cashier;
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.TRANSACTION_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			stage.setResizable(false);
			stage.setTitle("Movimentação");
			ControllerTransactionModal controller = (ControllerTransactionModal) loader.getController();
			controller.setTransaction(transaction);
			controller.setNewTransaction(newTransaction);
			controller.setRelation(relation);
			controller.setType(type);
			controller.setStage(stage);
			controller.setCashier(cashier);
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Boolean getNewTransaction() {
		return newTransaction;
	}

	public void setNewTransaction(Boolean newTransaction) {
		this.newTransaction = newTransaction;
	}

	public ControllerCashierRelationManager getRelation() {
		return relation;
	}

	public void setRelation(ControllerCashierRelationManager relation) {
		this.relation = relation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
}
