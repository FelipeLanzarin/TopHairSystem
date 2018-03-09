package br.ths.database;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ths.beans.Order;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class OrderDao {
	
	public boolean createOrder (Order order) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: duplicate key value violates unique constraint \"order_pkey\"")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.ID_ALREADY_EXIST);
					me.setExcepetionMessage("Código informado já está sendo utilizado por outro pedido.");
					throw me;
				}
				t = t.getCause();
			}
			LogTools.logError("erro ao inserir order no banco: "+ e.toString());
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
	
	public boolean updateOrder(Order order){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(order);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar order no banco: "+ e.toString());
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
	
	public boolean deleteOrder(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Order order = getOrder(id);
			if(order != null){
				em.getTransaction().begin();
				order = em.merge(order);
				em.remove(order);
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
	
	public Order getOrder(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Order order = null;
		try{
			order = em.find(Order.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter order no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return order;
	}
	@SuppressWarnings("unchecked")
	public List<Order> getOrders (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Order> order = null;
		try{
			Query query = em.createQuery("FROM Order ORDER BY id desc");
			order = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return order;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByDate (Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Order> order = null;
		try{
			Query query = em.createQuery("FROM Order "
					+ "where scheduler > :init and scheduler < :finalDate "
					+ "and isAttendance = true "
					+ "ORDER BY scheduler desc");
			query.setParameter("init", init);
			query.setParameter("finalDate", finalDate);
			order = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return order;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByCreationDate (Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Order> order = null;
		try{
			Query query = em.createQuery("FROM Order "
					+ "where creationDate > :init and creationDate < :finalDate "
					+ "ORDER BY creationDate desc");
			query.setParameter("init", init);
			query.setParameter("finalDate", finalDate);
			order = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return order;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByProfileId (Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Order> orders = null;
		String sql = "select * from ths_order o "
				+ "where o.profile_id = ? "
				+ "order by o.id desc";
		try{
			Query query = em.createNativeQuery(sql, Order.class);
			query.setParameter(1, id);
			orders = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return orders;
	}
	
	public  Double getSumOrderByDate(Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = "select sum(o.amount) from ths_order o "
				+ "where o.creation_date BETWEEN  ? and ? ";
		try{
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, init);
			query.setParameter(2, finalDate);
			return (Double) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return 0.0d;
	}
	
	public  Double getSumOrderPaiedByDate(Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = "select sum(p.amount_received) from ths_order o, payment p "
				+ "where o.id = p.order_id "
				+ "and o.creation_date BETWEEN  ? and ? ";
		try{
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, init);
			query.setParameter(2, finalDate);
			return (Double) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return 0.0d;
	}
	
	public  Double getSumOrderByDateAt(Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = "select sum(o.amount) from ths_order o "
				+ "where o.scheduler BETWEEN  ? and ? ";
		try{
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, init);
			query.setParameter(2, finalDate);
			return (Double) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return 0.0d;
	}
	
	public  Double getSumOrderByIds(List<Integer> ids){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = "select sum(o.amount) from ths_order o "
				+ "where o.id in(?1)";
		try{
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, ids);
			
			return (Double) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return 0.0d;
	}
	
	public  Double getSumOrderPaiedByIds(List<Integer> ids){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = "select sum(p.amount_received) from ths_order o, payment p "
				+ "where o.id in(?1) "
				+ "and o.id = p.order_id";
		try{
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, ids);
			return (Double) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return 0.0d;
	}
	
	public  Double getSumOrderPaiedByDateAt(Date init, Date finalDate){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = "select sum(p.amount_received) from ths_order o, payment p "
				+ "where o.id = p.order_id "
				+ "and o.scheduler BETWEEN  ? and ? ";
		try{
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, init);
			query.setParameter(2, finalDate);
			return (Double) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			LogTools.logError("erro ao obter orders no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return 0.0d;
	}

}
