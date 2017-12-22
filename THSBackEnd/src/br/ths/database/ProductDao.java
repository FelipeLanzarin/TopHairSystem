package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ths.beans.Product;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class ProductDao {
	
	public boolean createProduct (Product product) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: duplicate key value violates unique constraint \"product_pkey\"")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.ID_ALREADY_EXIST);
					me.setExcepetionMessage("Código informado já está sendo utilizado por outro produto.");
					throw me;
				}
				t = t.getCause();
			}
			LogTools.logError("erro ao inserir product no banco: "+ e.toString());
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
	
	public boolean updateProduct(Product product){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(product);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar product no banco: "+ e.toString());
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
	
	public boolean deleteProduct(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Product product = getProduct(id);
			if(product != null){
				em.getTransaction().begin();
				product = em.merge(product);
				em.remove(product);
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
	
	public Product getProduct(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Product product = null;
		try{
			product = em.find(Product.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter product no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return product;
	}
	@SuppressWarnings("unchecked")
	public List<Product> getProducts (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Product> product = null;
		try{
			Query query = em.createQuery("FROM Product ORDER BY name");
			product = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter products no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return product;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductsBySubCategoryId (Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Product> products = null;
		String sql = "select * from product p "
				+ "where p.sub_category_id = ? "
				+ "order by p.name";
		try{
			Query query = em.createNativeQuery(sql, Product.class);
			query.setParameter(1, id);
			products = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter products no banco: "+ e.toString());
			LogTools.logError(e);
		}finally{
			em.close();
		}
		return products;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductsByCategoryId (Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Product> products = null;
		String sql = "select * from product p, sub_category sc "
				+ "where p.sub_category_id = sc.id "
				+ "and sc.category_id = ? "
				+ "order by p.name";
		try{
			Query query = em.createNativeQuery(sql, Product.class);
			query.setParameter(1, id);
			products = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter products no banco: "+ e.toString());
			LogTools.logError(e);
		}finally{
			em.close();
		}
		return products;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getNewProductId(){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Integer> maxProduc = null;
		try{
			Query query = em.createQuery("select max(id) from Product");
			maxProduc = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter products no banco: "+ e.toString());
		}finally{
			em.close();
		}
		Integer max = 1000;
		if(maxProduc == null || maxProduc.isEmpty()){
			return max;
		}
		try{
			max =  maxProduc.get(0)+1;
			if(max<1000){
				max=1000;
			}
		}catch (Exception e) {
			return 10000;
		}
		return max;
	}

}
