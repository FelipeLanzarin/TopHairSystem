package br.ths.database;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ths.beans.Payment;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class PaymentDao {
	
	public boolean createPayment (Payment payment) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(payment);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: duplicate key value violates unique constraint \"payment_pkey\"")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.ID_ALREADY_EXIST);
					me.setExcepetionMessage("Código informado já está sendo utilizado por outro pagamento.");
					throw me;
				}
				t = t.getCause();
			}
			LogTools.logError("erro ao inserir payment no banco: "+ e.toString());
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
	
	public boolean updatePayment(Payment payment){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(payment);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar payment no banco: "+ e.toString());
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
	
	public boolean deletePayment(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Payment payment = getPayment(id);
			if(payment != null){
				em.getTransaction().begin();
				payment = em.merge(payment);
				em.remove(payment);
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
	
	public Payment getPayment(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Payment payment = null;
		try{
			payment = em.find(Payment.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter payment no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return payment;
	}
	@SuppressWarnings("unchecked")
	public List<Payment> getPayments (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Payment> payment = null;
		try{
			Query query = em.createQuery("FROM Payment ORDER BY id desc");
			payment = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter payments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return payment;
	}
	
	public List<Payment> getPaymentsByDate (Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Payment> payment = null;
		try{
//			Query query = em.createQuery("FROM Payment "
//					+ "where scheduler > :init and scheduler < :finalDate "
//					+ "and isAttendance = true "
//					+ "ORDER BY scheduler");
//			query.setParameter("init", init);
//			query.setParameter("finalDate", finalDate);
//			payment = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter payments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return payment;
	}
	

	@SuppressWarnings("unchecked")
	public Payment getPaymentByOrder (Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Payment> payment = null;
		try{
			String sql = "select * from payment p "
					+ "where p.order_id = ? "
					+ "order by p.id";
			Query query = em.createNativeQuery(sql, Payment.class);
			query.setParameter(1, id);
			payment = query.getResultList();
			if(payment != null && !payment.isEmpty()){
				return payment.get(0);
			}
		}catch (Exception e) {
			LogTools.logError("erro ao obter payments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return null;
	}

}
