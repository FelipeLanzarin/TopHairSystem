package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.UserBranch;
import br.ths.tools.log.LogTools;



public class UserBranchDao {
	
	public boolean createUserBranch (UserBranch userBranch){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(userBranch);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir userBranch no banco: "+ e.toString());
			e.printStackTrace();
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
	
	public boolean updateUserBranch(UserBranch userBranch){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(userBranch);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar userBranch no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteUserBranch(String id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			UserBranch userBranch = getUserBranch(id);
			if(userBranch != null){
				em.getTransaction().begin();
				userBranch = em.merge(userBranch);
				em.remove(userBranch);
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
	
	public UserBranch getUserBranch(String id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		UserBranch userBranch = null;
		try{
			userBranch = em.find(UserBranch.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter userBranch no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return userBranch;
	}
	@SuppressWarnings("unchecked")
	public List<UserBranch> getUserBranchs (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<UserBranch> userBranch = null;
		try{
			Query query = em.createQuery("FROM UserBranch ORDER BY login");
			userBranch = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter userBranchs no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return userBranch;
	}	
	
}
