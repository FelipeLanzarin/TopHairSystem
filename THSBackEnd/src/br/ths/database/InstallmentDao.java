package br.ths.database;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ths.beans.Installment;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class InstallmentDao {
	
	public boolean createInstallment (Installment installment) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(installment);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: duplicate key value violates unique constraint \"installment_pkey\"")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.ID_ALREADY_EXIST);
					me.setExcepetionMessage("Código informado já está sendo utilizado por outro parcela.");
					throw me;
				}
				t = t.getCause();
			}
			LogTools.logError("erro ao inserir installment no banco: "+ e.toString());
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
	
	public boolean updateInstallment(Installment installment){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(installment);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar installment no banco: "+ e.toString());
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
	
	public boolean deleteInstallment(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Installment installment = getInstallment(id);
			if(installment != null){
				em.getTransaction().begin();
				installment = em.merge(installment);
				em.remove(installment);
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
	
	public Installment getInstallment(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Installment installment = null;
		try{
			installment = em.find(Installment.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter installment no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return installment;
	}
	@SuppressWarnings("unchecked")
	public List<Installment> getInstallments (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Installment> installment = null;
		try{
			Query query = em.createQuery("FROM Installment ORDER BY id desc");
			installment = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter installments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return installment;
	}
	
	@SuppressWarnings("unchecked")
	public List<Installment> getInstallmentsByDate (Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Installment> installment = null;
		try{
			Query query = em.createQuery("FROM Installment "
					+ "where scheduler > :init and scheduler < :finalDate "
					+ "and isAttendance = true "
					+ "ORDER BY scheduler");
			query.setParameter("init", init);
			query.setParameter("finalDate", finalDate);
			installment = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter installments no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return installment;
	}
	
	@SuppressWarnings("unchecked")
	public List<Installment> getInstallmentsByOrder(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Installment> installment = null;
		try{
			String sql = "select * from installment i "
					+ "where i.order_id = ? "
					+ "order by i.id";
			Query query = em.createNativeQuery(sql, Installment.class);
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
	public List<Installment> getInstallmentsByPayment(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Installment> installment = null;
		try{
			String sql = "select * from installment i "
					+ "where i.payment_id = ? "
					+ "order by i.id";
			Query query = em.createNativeQuery(sql, Installment.class);
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
