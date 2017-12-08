package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.SubCategory;
import br.ths.tools.log.LogTools;



public class SubCategoryDao {
	
	public boolean createSubCategory (SubCategory subCategory){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(subCategory);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir subCategory no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	public boolean updateSubCategory(SubCategory subCategory){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(subCategory);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar subCategory no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteSubCategory(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			SubCategory subCategory = getSubCategory(id);
			if(subCategory != null){
				em.getTransaction().begin();
				subCategory = em.merge(subCategory);
				em.remove(subCategory);
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
	
	public SubCategory getSubCategory(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		SubCategory subCategory = null;
		try{
			subCategory = em.find(SubCategory.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter subCategory no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return subCategory;
	}
	
	@SuppressWarnings("unchecked")
	public List<SubCategory> getSubCategories(){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<SubCategory> subCategory = null;
		try{
			Query query = em.createQuery("FROM SubCategory ORDER BY name");
			subCategory = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter subCategorys no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return subCategory;
	}
	
	@SuppressWarnings("unchecked")
	public List<SubCategory> getSubCategoriesByCategoryId(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<SubCategory> subCategory = null;
		String sql = "select * from sub_category sc "
				+ "where sc.category_id = ? "
				+ "order by sc.name";
		try{
			Query query = em.createNativeQuery(sql, SubCategory.class);
			query.setParameter(1, id);
			subCategory = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter subCategorys no banco: "+ e.toString());
			LogTools.logError(e);
		}finally{
			em.close();
		}
		return subCategory;
	}

}
