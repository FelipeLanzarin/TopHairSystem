package br.ths.beans.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ths.beans.Cashier;
import br.ths.beans.PaymentMethod;
import br.ths.beans.Transaction;
import br.ths.database.TransactionDao;
import br.ths.exceptions.ManagersExceptions;

public class TransactionManager {
	
	private static TransactionDao cd;
	
	public static Boolean update(Transaction transaction, Double lastValue) {
		Double diff = transaction.getAmount() - lastValue;
		Cashier cashier = transaction.getCashier();
		Double newBalance =  cashier.getBalance() + diff;
		newBalance = OrderManager.roundValue(newBalance);
		if(transaction.getPaymentMethod()== null){
			Double newAmount =  cashier.getAmountInCashier() + diff;
			newAmount = OrderManager.roundValue(newAmount);
			cashier.setAmountInCashier(newAmount);
		}else{
			if(transactionIsMoney(transaction)){
				Double newAmount =  cashier.getAmountInCashier() + diff;
				newAmount = OrderManager.roundValue(newAmount);
				cashier.setAmountInCashier(newAmount);
			}
		}
		cashier.setBalance(newBalance);
		if(CashierManager.update(cashier)){
			return Boolean.valueOf(getTransactionDao().updateTransaction(transaction));
		}
		return false;
	}

	public static Boolean create(Transaction transaction) {
		Cashier cashier = transaction.getCashier();
		transaction.setTime(new Date());
		Double newBalance =  cashier.getBalance() + transaction.getAmount();
		newBalance = OrderManager.roundValue(newBalance);
		if(transaction.getPaymentMethod()== null){
			Double newAmount =  cashier.getAmountInCashier() + transaction.getAmount();
			newAmount = OrderManager.roundValue(newAmount);
			cashier.setAmountInCashier(newAmount);
		}else{
			if(transactionIsMoney(transaction)){
				Double newAmount =  cashier.getAmountInCashier() + transaction.getAmount();
				newAmount = OrderManager.roundValue(newAmount);
				cashier.setAmountInCashier(newAmount);
			}
		}
		cashier.setBalance(newBalance);
		if(CashierManager.update(cashier)){
			return Boolean.valueOf(getTransactionDao().createTransaction(transaction));
		}
		return false;
	}

	public static Boolean delete(Transaction transaction) throws ManagersExceptions {
		Cashier cashier = transaction.getCashier();
		Double newBalance =  cashier.getBalance() - transaction.getAmount();
		newBalance = OrderManager.roundValue(newBalance);
		if(transaction.getPaymentMethod()== null){
			Double newAmount =  cashier.getAmountInCashier() - transaction.getAmount();
			newAmount = OrderManager.roundValue(newAmount);
			cashier.setAmountInCashier(newAmount);
		}else{
			if(transactionIsMoney(transaction)){
				Double newAmount =  cashier.getAmountInCashier() - transaction.getAmount();
				newAmount = OrderManager.roundValue(newAmount);
				cashier.setAmountInCashier(newAmount);
			}
		}
		cashier.setBalance(newBalance);
		if(CashierManager.update(cashier)){
			return Boolean.valueOf(getTransactionDao().deleteTransaction(transaction.getId()));
		}
		return null;
	}
	
	public static List<Transaction> getTransactionsByDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    Date init = calendar.getTime();
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    Date finalDate = calendar.getTime();
		return getTransactionDao().getTransactionsByDate(init,finalDate);
	}
	
	public static Transaction getTransactionByPaymentMethod(PaymentMethod paymentMethod){
		if(paymentMethod == null){
			return null;
		}
		return getTransactionDao().getTransactionByPaymentMethodId(paymentMethod.getId());
	}
	
	public static String getType(String type){
		String paymentType = "output";
		if("Entrada".equals(type)){
			paymentType = "input";
		}
		
		return paymentType;
	}
	
	public static Boolean transactionIsMoney(Transaction transaction){
		if(transaction == null || transaction.getPaymentMethod() == null){
			return true;
		}
		if(transaction.getPaymentMethod().getType().equals("cash") || transaction.getPaymentMethod().getType().equals("bankCheck")){
			return true;
		}
		return false;
	}
	
	public static String getDescriptionType(String type){
		String paymentType = "Saída";
		if("input".equals(type)){
			paymentType = "Entrada";
		}
		
		return paymentType;
	}
	
	private static TransactionDao getTransactionDao(){
		if(cd == null){
			cd = new TransactionDao();
		}
		return cd;
	}
}
