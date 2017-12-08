package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.Category;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class CategoryDao {
	
	public boolean createCategory (Category category){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(category);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir category no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	public boolean updateCategory(Category category){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(category);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar category no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteCategory(Integer id) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Category category = getCategory(id);
			if(category != null){
				em.getTransaction().begin();
				category = em.merge(category);
				em.remove(category);
				em.getTransaction().commit();
			}
		}catch (Exception e) {
			LogTools.logError("erro ao excluir : "+ e.toString());
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: update or delete on table \"category\" violates foreign key constraint")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.CITY_IN_USE);
					me.setExcepetionMessage("Essa categoria já possui uma subcategoria. Por esse motivo a mesma não pode ser excluída");
					throw me;
				}
				t = t.getCause();
			}
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
	
	public Category getCategory(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Category category = null;
		try{
			category = em.find(Category.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter category no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return category;
	}
	@SuppressWarnings("unchecked")
	public List<Category> getCategories (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Category> category = null;
		try{
			Query query = em.createQuery("FROM Category ORDER BY name");
			category = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter categorys no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return category;
	}

}
