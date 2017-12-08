package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.Product;
import br.ths.tools.log.LogTools;



public class ProductDao {
	
	public boolean createProduct (Product product){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir product no banco: "+ e.toString());
			em.getTransaction().rollback();
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
			em.getTransaction().rollback();
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
