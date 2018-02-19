package br.ths.screens.order.payment;

import java.net.URL;

import br.ths.beans.Installment;
import br.ths.beans.Order;
import br.ths.beans.Payment;
import br.ths.beans.PaymentMethod;
import br.ths.controllers.order.payment.ControllerPaymentMethodModal;
import br.ths.tools.log.LogTools;
import br.ths.utils.XmlPathUtils;
import fx.tools.controller.GenericController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenPaymentMethodModal extends Application {
	
	private Order order;
	private Payment payment;
	private Installment installment;
	private PaymentMethod paymentMethod;
	private Boolean newPaymentMethod;
	private GenericController controller;
	
	@Override
	public void start(Stage stage) {
		try {
			URL arquivoFXML = getClass().getResource(XmlPathUtils.PAYMENT_METHOD_MODAL);
			FXMLLoader loader = new FXMLLoader(arquivoFXML);
			Parent fxmlParent = (Parent) loader.load();
			stage.setScene(new Scene(fxmlParent));
			ControllerPaymentMethodModal control = (ControllerPaymentMethodModal) loader.getController();
			control.setRelation(controller);
			control.setNewPaymentMethod(newPaymentMethod);
			control.setOrder(order);
			control.setPayment(payment);
			control.setPaymentMethod(paymentMethod);
			control.setInstallment(installment);
			control.setStage(stage);
			stage.setResizable(false);
			stage.setTitle("Pagamento");
			stage.show();
		} catch(Exception e) {
			LogTools.logError(e);
		}
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Boolean getNewPaymentMethod() {
		return newPaymentMethod;
	}

	public void setNewPaymentMethod(Boolean newPaymentMethod) {
		this.newPaymentMethod = newPaymentMethod;
	}

	public GenericController getController() {
		return controller;
	}

	public void setController(GenericController controller) {
		this.controller = controller;
	}
	
}
