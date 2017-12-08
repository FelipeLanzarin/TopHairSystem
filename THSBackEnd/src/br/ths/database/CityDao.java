package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.City;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class CityDao {
	
	public boolean createCity (City city){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(city);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir city no banco: "+ e.toString());
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
	
	public boolean updateCity(City city){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(city);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar city no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteCity(Integer id) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			City city = getCity(id);
			if(city != null){
				em.getTransaction().begin();
				city = em.merge(city);
				em.remove(city);
				em.getTransaction().commit();
			}
		}catch (Exception e) {
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: update or delete on table \"city\" violates foreign key constraint")) {
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
	
	public City getCity(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		City city = null;
		try{
			city = em.find(City.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter city no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return city;
	}
	@SuppressWarnings("unchecked")
	public List<City> getCities (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<City> city = null;
		try{
			Query query = em.createQuery("FROM City ORDER BY name");
			city = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter citys no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return city;
	}

}
