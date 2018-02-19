package br.ths.database;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ths.beans.PaymentMethod;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class PaymentMethodDao {
	
	public boolean createPaymentMethod (PaymentMethod paymentMethod) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(paymentMethod);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: duplicate key value violates unique constraint \"paymentMethod_pkey\"")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.ID_ALREADY_EXIST);
					me.setExcepetionMessage("Código informado já está sendo utilizado por outro metodo de pagamento.");
					throw me;
				}
				t = t.getCause();
			}
			LogTools.logError("erro ao inserir paymentMethod no banco: "+ e.toString());
			LogTools.logError(e);
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
	
	public boolean updatePaymentMethod(PaymentMethod paymentMethod){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(paymentMethod);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar paymentMethod no banco: "+ e.toString());
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
	
	public boolean deletePaymentMethod(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			PaymentMethod paymentMethod = getPaymentMethod(id);
			if(paymentMethod != null){
				em.getTransaction().begin();
				paymentMethod = em.merge(paymentMethod);
				em.remove(paymentMethod);
				em.getTransaction().commit();
			}
		}catch (Exception e) {
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
	
	public PaymentMethod getPaymentMethod(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		PaymentMethod paymentMethod = null;
		try{
			paymentMethod = em.find(PaymentMethod.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter paymentMethod no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return paymentMethod;
	}
	@SuppressWarnings("unchecked")
	public List<PaymentMethod> getPaymentMethods (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<PaymentMethod> paymentMethod = null;
		try{
			Query query = em.createQuery("FROM PaymentMethod ORDER BY id desc");
			paymentMethod = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter paymentMethods no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return paymentMethod;
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentMethod> getPaymentMethodsByDate (Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<PaymentMethod> paymentMethod = null;
		try{
			Query query = em.createQuery("FROM PaymentMethod "
					+ "where scheduler > :init and scheduler < :finalDate "
					+ "and isAttendance = true "
					+ "ORDER BY scheduler");
			query.setParameter("init", init);
			query.setParameter("finalDate", finalDate);
			paymentMethod = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter paymentMethods no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return paymentMethod;
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentMethod> getPaymentMethodsByOrder(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<PaymentMethod> installment = null;
		try{
			String sql = "select * from payment_method pm "
					+ "where pm.order_id = ? "
					+ "order by pm.id";
			Query query = em.createNativeQuery(sql, PaymentMethod.class);
			query.setParameter(1, id);
			installment = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter installments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return installment;
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentMethod> getPaymentMethodsByPayment(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<PaymentMethod> installment = null;
		try{
			String sql = "select * from payment_method pm "
					+ "where pm.payment_id = ? "
					+ "order by pm.id";
			Query query = em.createNativeQuery(sql, PaymentMethod.class);
			query.setParameter(1, id);
			installment = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter installments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return installment;
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentMethod> getPaymentMethodByInstallment(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<PaymentMethod> installment = null;
		try{
			String sql = "select * from payment_method pm "
					+ "where pm.installment_id = ? "
					+ "order by pm.id";
			Query query = em.createNativeQuery(sql, PaymentMethod.class);
			query.setParameter(1, id);
			installment = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter installments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return installment;
	}

}
