package br.ths.database;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.Transaction;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class TransactionDao {
	
	public boolean createTransaction (Transaction transaction){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(transaction);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir transaction no banco: "+ e.toString());
			try{
				em.getTransaction().rollback();
			}catch (Exception ex) {
			}
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	public boolean updateTransaction(Transaction transaction){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(transaction);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar transaction no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteTransaction(Integer id) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Transaction transaction = getTransaction(id);
			if(transaction != null){
				em.getTransaction().begin();
				transaction = em.merge(transaction);
				em.remove(transaction);
				em.getTransaction().commit();
			}
		}catch (Exception e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: update or delete on table \"transaction\" violates foreign key constraint")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.CITY_IN_USE);
					me.setExcepetionMessage("Essa cidade já está sendo utilizada em outro lugar.");
					throw me;
				}
				t = t.getCause();
			}
			LogTools.logError("erro ao excluir : "+ e.toString());
			try{
				em.getTransaction().rollback();
			}catch (Exception ex) {
				LogTools.logError("erro ao  dar roolback: "+ e.toString());
			}
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public Transaction getTransaction(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Transaction transaction = null;
		try{
			transaction = em.find(Transaction.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter transaction no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return transaction;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactionsByDate(Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Transaction> transactions = null;
		try{
			Query query = em.createQuery("FROM Transaction "
					+ "where time > :init and time < :finalDate "
					+ "ORDER BY time");
			query.setParameter("init", init);
			query.setParameter("finalDate", finalDate);
			transactions = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter transactions no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return transactions;
	}
	
	@SuppressWarnings("unchecked")
	public Transaction getTransactionByPaymentMethodId(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Transaction> transactions = null;
		String sql = "select * from transaction t "
				+ "where t.payment_method_id = ? ";
		try{
			Query query = em.createNativeQuery(sql, Transaction.class);
			query.setParameter(1, id);
			transactions = query.getResultList();
			if(transactions != null && !transactions.isEmpty()){
				return transactions.get(0);
			}
		}catch (Exception e) {
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return null;
	}

}
