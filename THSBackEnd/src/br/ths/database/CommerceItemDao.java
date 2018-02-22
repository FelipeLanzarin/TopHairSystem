package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ths.beans.CommerceItem;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class CommerceItemDao {
	
	public boolean createCommerceItem (CommerceItem commerceItem) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(commerceItem);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: duplicate key value violates unique constraint \"commerceItem_pkey\"")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.ID_ALREADY_EXIST);
					me.setExcepetionMessage("Código informado já está sendo utilizado por outro commerceItem.");
					throw me;
				}
				t = t.getCause();
			}
			LogTools.logError("erro ao inserir commerceItem no banco: "+ e.toString());
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
	
	public boolean updateCommerceItem(CommerceItem commerceItem){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(commerceItem);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar commerceItem no banco: "+ e.toString());
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
	
	public boolean deleteCommerceItem(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			CommerceItem commerceItem = getCommerceItem(id);
			if(commerceItem != null){
				em.getTransaction().begin();
				commerceItem = em.merge(commerceItem);
				em.remove(commerceItem);
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
	
	public boolean deleteCommerceItem(List<CommerceItem> cis){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			if(cis == null){
				return false;
			}
			em.getTransaction().begin();
			for (CommerceItem commerceItem : cis) {
				commerceItem = em.merge(commerceItem);
				em.remove(commerceItem);
			}
			em.getTransaction().commit();
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
	
	public CommerceItem getCommerceItem(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		CommerceItem commerceItem = null;
		try{
			commerceItem = em.find(CommerceItem.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter commerceItem no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return commerceItem;
	}
	@SuppressWarnings("unchecked")
	public List<CommerceItem> getCommerceItems (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<CommerceItem> commerceItem = null;
		try{
			Query query = em.createQuery("FROM CommerceItem ORDER BY name");
			commerceItem = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter commerceItems no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return commerceItem;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommerceItem> getCommerceItemsByOrderId (Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<CommerceItem> commerceItems = null;
		String sql = "select * from commerce_item ci "
				+ "where ci.order_id = ? "
				+ "order by ci.id";
		try{
			Query query = em.createNativeQuery(sql, CommerceItem.class);
			query.setParameter(1, id);
			commerceItems = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter commerceItems no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return commerceItems;
	}

}
