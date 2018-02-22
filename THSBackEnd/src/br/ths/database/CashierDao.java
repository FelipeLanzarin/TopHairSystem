package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.Cashier;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class CashierDao {
	
	public boolean createCashier (Cashier cashier){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(cashier);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir cashier no banco: "+ e.toString());
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
	
	public boolean updateCashier(Cashier cashier){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(cashier);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar cashier no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteCashier(Integer id) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Cashier cashier = getCashier(id);
			if(cashier != null){
				em.getTransaction().begin();
				cashier = em.merge(cashier);
				em.remove(cashier);
				em.getTransaction().commit();
			}
		}catch (Exception e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: update or delete on table \"cashier\" violates foreign key constraint")) {
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
	
	public Cashier getCashier(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Cashier cashier = null;
		try{
			cashier = em.find(Cashier.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter cashier no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return cashier;
	}
	
	@SuppressWarnings("unchecked")
	public Cashier getCashierByBranchId(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Cashier> cashiers = null;
		String sql = "select * from cashier c "
				+ "where c.branch_company_id = ? ";
		try{
			Query query = em.createNativeQuery(sql, Cashier.class);
			query.setParameter(1, id);
			cashiers = query.getResultList();
			if(cashiers != null && !cashiers.isEmpty()){
				return cashiers.get(0);
			}
		}catch (Exception e) {
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return null;
	}

}
