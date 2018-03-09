package br.ths.beans.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.ths.beans.BranchCompany;
import br.ths.beans.Cashier;
import br.ths.beans.Installment;
import br.ths.beans.Order;
import br.ths.beans.Payment;
import br.ths.beans.PaymentMethod;
import br.ths.beans.Transaction;
import br.ths.database.InstallmentDao;
import br.ths.database.PaymentDao;
import br.ths.database.PaymentMethodDao;
import br.ths.exceptions.ManagersExceptions;


/**
 * 
 * Payment é aberta com todos os valores zerados e já é criada no banco de dados.
 * A cada nova adição de um commerceItem, todos os calculos já serão refeitos no PaymentManager e salvados no banco.
 * Se excluir a payment, deve se excluir os commerceItens
 * 
 * @author Felipe Lazarin
 *
 */

public class PaymentManager {
	
	private static PaymentDao pd;
	private static InstallmentDao id;
	private static PaymentMethodDao pmd;
	
	public static Boolean update(Payment payment) throws ManagersExceptions {
//		validateValues(payment);
		return Boolean.valueOf(getPaymentDao().updatePayment(payment));
	}
	
	public static Boolean create(Payment payment) throws ManagersExceptions {
		if(payment == null || payment.getOrder() == null){
			return false;
		}
		payment.setAmount(payment.getOrder().getAmount());
		payment.setCreationDate(new Date());
		payment.setAmountReceived(0.0d);
		return Boolean.valueOf(getPaymentDao().createPayment(payment));
	}

	public static Boolean delete(Payment payment) {
		return Boolean.valueOf(getPaymentDao().deletePayment(payment.getId()));
	}
	
	public static List<Payment> getPayments() {
		return getPaymentDao().getPayments();
	}
	
	public static Payment getPaymentByOrder(Order order) {
		if(order == null){
			return null;
		}
		return getPaymentDao().getPaymentByOrder(order.getId());
	}
	
	public static Boolean update(Installment installment) throws ManagersExceptions {
//		validateValues(installment);
		return Boolean.valueOf(getInstallmentDao().updateInstallment(installment));
	}
	
	public static Boolean create(Installment installment) throws ManagersExceptions {
		if(installment == null){
			return false;
		}
		installment.setPaymentDate(new Date());
		installment.setAmountPayed(0.0d);
		return Boolean.valueOf(getInstallmentDao().createInstallment(installment));
	}

	public static Boolean delete(Installment installment) {
		return Boolean.valueOf(getInstallmentDao().deleteInstallment(installment.getId()));
	}
	
	public static List<Installment> getInstallments() {
		return getInstallmentDao().getInstallments();
	}
	
	public static List<Installment> getInstallmentsByOrder(Order order) {
		if(order == null){
			return null;
		}
		return getInstallmentDao().getInstallmentsByOrder(order.getId());
	}
	
	public static List<Installment> getInstallmentsByPayment(Payment payment) {
		if(payment == null){
			return null;
		}
		return getInstallmentDao().getInstallmentsByPayment(payment.getId());
	}
	
	public static Boolean update(PaymentMethod paymentMethod, Double lastValue) throws ManagersExceptions {
		Double diff = paymentMethod.getAmount() - lastValue;
		if(validateValue(paymentMethod, diff)){
			if(Boolean.valueOf(getPaymentMethodDao().updatePaymentMethod(paymentMethod))){
				Installment installment = paymentMethod.getInstallment();
				Double newAmountInstallmentPayed = installment.getAmountPayed() + diff;
				newAmountInstallmentPayed = roundValue(newAmountInstallmentPayed);
				installment.setAmountPayed(newAmountInstallmentPayed);
				if(installment.getAmount().equals(installment.getAmountPayed())){
					installment.setPayDate(new Date());
				}
				Payment payment = paymentMethod.getPayment();
				Double newAmountPaymentPayed = payment.getAmountReceived() + diff;
				newAmountPaymentPayed = roundValue(newAmountPaymentPayed);
				payment.setAmountReceived(newAmountPaymentPayed);
				payment.setLastPaymentDate(new Date());
				update(installment);
				update(payment);
				updateTransaction(paymentMethod);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * cria payment method e faz todos os ajustes necessários
	 * @param paymentMethod
	 * @return
	 * @throws ManagersExceptions
	 */
	public static Boolean create(PaymentMethod paymentMethod) throws ManagersExceptions {
		if(paymentMethod == null){
			return false;
		}
		if(validateValue(paymentMethod, paymentMethod.getAmount())){
			if(getPaymentMethodDao().createPaymentMethod(paymentMethod)){
				Installment installment = paymentMethod.getInstallment();
				Double newAmountInstallmentPayed = installment.getAmountPayed() + paymentMethod.getAmount();
				newAmountInstallmentPayed = roundValue(newAmountInstallmentPayed);
				installment.setAmountPayed(newAmountInstallmentPayed);
				if(installment.getAmount().equals(installment.getAmountPayed())){
					installment.setPayDate(new Date());
				}
				Payment payment = paymentMethod.getPayment();
				Double newAmountPaymentPayed = payment.getAmountReceived() + paymentMethod.getAmount();
				newAmountPaymentPayed = roundValue(newAmountInstallmentPayed);
				payment.setAmountReceived(newAmountPaymentPayed);
				payment.setLastPaymentDate(new Date());
				update(installment);
				update(payment);
				createTransaction(paymentMethod);
				return true;
			}
		}
		return false;
	}

	public static Boolean delete(PaymentMethod paymentMethod) throws ManagersExceptions {
		Double amountDelete=paymentMethod.getAmount();
		if(deleteTransaction(paymentMethod)){
			if(getPaymentMethodDao().deletePaymentMethod(paymentMethod.getId())){
				Installment installment = paymentMethod.getInstallment();
				Double newAmountInstallmentPayed = installment.getAmountPayed() - amountDelete;
				newAmountInstallmentPayed = roundValue(newAmountInstallmentPayed);
				installment.setAmountPayed(newAmountInstallmentPayed);
				if(installment.getAmount().equals(installment.getAmountPayed())){
					installment.setPayDate(new Date());
				}
				Payment payment = paymentMethod.getPayment();
				Double newAmountPaymentPayed = payment.getAmountReceived() - amountDelete;
				newAmountPaymentPayed = roundValue(newAmountPaymentPayed);
				payment.setAmountReceived(newAmountPaymentPayed);
				payment.setLastPaymentDate(new Date());
				update(installment);
				update(payment);
				
				return true;
			}
		}
		return false;
	}
	
	public static List<PaymentMethod> getPaymentMethods() {
		return getPaymentMethodDao().getPaymentMethods();
	}
	
	public static List<PaymentMethod> getPaymentMethodsByOrder(Order order) {
		if(order == null){
			return null;
		}
		return getPaymentMethodDao().getPaymentMethodsByOrder(order.getId());
	}
	
	public static List<PaymentMethod> getPaymentMethodsByPayment(Payment payment) {
		if(payment == null){
			return null;
		}
		return getPaymentMethodDao().getPaymentMethodsByPayment(payment.getId());
	}
	
	private static Boolean validateValue(PaymentMethod paymentMethod, Double value) throws ManagersExceptions{
		if(paymentMethod == null || value == null){
			return false;
		}
		Double valuePayed = valuePayedByOrder(paymentMethod.getOrder());
		Double nextAmout = valuePayed + value;
		int i2 = nextAmout.compareTo(paymentMethod.getOrder().getAmount());

	    if(i2 <= 0) {
	    	return true;
	    } 
	    
		ManagersExceptions me = new ManagersExceptions();
		me.setId(17);
		me.setExcepetionMessage("Valor do pagamento irá ultrapassar o valor do pedido");
		throw me;
	}
	
	private static Boolean createTransaction(PaymentMethod paymentMethod) throws ManagersExceptions{
		Transaction t = new Transaction();
		t.setAmount(paymentMethod.getAmount());
		t.setType("input");
		t.setCashier(getCashierByPaymentMethod(paymentMethod));
		t.setPaymentMethod(paymentMethod);
		TransactionManager.create(t);
		return true;
	}
	
	private static Boolean updateTransaction(PaymentMethod paymentMethod) throws ManagersExceptions{
		Transaction t = getTransactionByPaymentMethod(paymentMethod);
		if(t == null){
			return false;
		}
		Double lastValue = t.getAmount();
		t.setAmount(paymentMethod.getAmount());
		t.setType("input");
		t.setCashier(getCashierByPaymentMethod(paymentMethod));
		t.setPaymentMethod(paymentMethod);
		TransactionManager.update(t, lastValue);
		return true;
	}
	
	private static Boolean deleteTransaction(PaymentMethod paymentMethod) throws ManagersExceptions{
		Transaction t = getTransactionByPaymentMethod(paymentMethod);
		if(t == null){
			return false;
		}
		return TransactionManager.delete(t);
	}
	
	private static Cashier getCashierByPaymentMethod(PaymentMethod paymentMethod) throws ManagersExceptions{
		BranchCompany bc = paymentMethod.getOrder().getEmployee().getBranchCompany();
		return CashierManager.getCashierByBranch(bc);
	}
	
	private static Transaction getTransactionByPaymentMethod(PaymentMethod paymentMethod){
		return TransactionManager.getTransactionByPaymentMethod(paymentMethod);
	}
	
	public static Double valuePayedByOrder(Order order){
		Double amount=0.0d;
		for (PaymentMethod paymentMethod : getPaymentMethodsByOrder(order)) {
			amount += paymentMethod.getAmount();
		}
		return amount;
	}

	public static Double roundValue(Double value){
		if(value == null){
			return 0.0d;
		}
		BigDecimal vb = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return vb.doubleValue();
	}
	
	public static String getTypePaymentMethod(String type){
		String paymentType = "";
		switch (type) {
		case "Cartão de Crédito":
			paymentType = "creditCard";
			break;
		case "Cartão de Débito":
			paymentType = "debit";
			break;
		case "Dinheiro":
			paymentType = "cash";
			break;
		case "Cheque":
			paymentType = "bankCheck";
			break;
			
		default:
			paymentType = "other";
			break;
		
		}
		return paymentType;
	}
	
	public static String getDescriptionPaymentMethod(String type){
		String paymentType = "";
		switch (type) {
		case "creditCard":
			paymentType = "Cartão de Crédito";
			break;
		case "debit":
			paymentType = "Cartão de Débito";
			break;
		case "cash":
			paymentType = "Dinheiro";
			break;
		case "bankCheck":
			paymentType = "Cheque";
			break;
			
		default:
			paymentType = "Outro";
			break;
		
		}
		return paymentType;
	}
	
	public static String getDescriptionByPaymentMethod(PaymentMethod paymentMethod){
		String paymentType = "";
		switch (paymentMethod.getType()) {
		case "creditCard":
			paymentType = "Cartão de Crédito";
			break;
		case "cash":
			paymentType = "Dinheiro";
			break;
		case "debit":
			paymentType = "Cartão de Débito";
			break;
		case "bankCheck":
			paymentType = "Cheque";
			break;
		default:
			paymentType = "Outro";
			break;
		}
		
		String description = "Pagemento do pedido "+paymentMethod.getOrder().getId()+" com "+ paymentType;
		return description;
	}
	
	private static PaymentDao getPaymentDao(){
		if(pd == null){
			pd = new PaymentDao();
		}
		return pd;
	}
	
	private static InstallmentDao getInstallmentDao(){
		if(id == null){
			id = new InstallmentDao();
		}
		return id;
	}
	
	private static PaymentMethodDao getPaymentMethodDao(){
		if(pmd == null){
			pmd = new PaymentMethodDao();
		}
		return pmd;
	}
	
}
