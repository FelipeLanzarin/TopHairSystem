package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.Profile;
import br.ths.tools.log.LogTools;



public class ProfileDao {
	
	public boolean createProfile (Profile profile){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(profile);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir profile no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	public boolean updateProfile(Profile profile){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(profile);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar profile no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteProfile(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Profile profile = getProfile(id);
			if(profile != null){
				em.getTransaction().begin();
				profile = em.merge(profile);
				em.remove(profile);
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
	
	public Profile getProfile(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Profile profile = null;
		try{
			profile = em.find(Profile.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter profile no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return profile;
	}
	@SuppressWarnings("unchecked")
	public List<Profile> getProfiles (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Profile> profile = null;
		try{
			Query query = em.createQuery("FROM Profile ORDER BY name");
			profile = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter profiles no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return profile;
	}

}
