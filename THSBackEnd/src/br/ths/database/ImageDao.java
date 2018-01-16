package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.Image;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;



public class ImageDao {
	
	public boolean createImage (Image image){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(image);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir image no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	public boolean updateImage(Image image){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(image);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar image no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteImage(Integer id) throws ManagersExceptions{
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Image image = getImage(id);
			if(image != null){
				em.getTransaction().begin();
				image = em.merge(image);
				em.remove(image);
				em.getTransaction().commit();
			}
		}catch (Exception e) {
			LogTools.logError("erro ao excluir : "+ e.toString());
			Throwable t = e;
			while (t != null) {
				if (t.getMessage().contains("ERROR: update or delete on table \"image\" violates foreign key constraint")) {
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.CITY_IN_USE);
					me.setExcepetionMessage("Essa categoria j� possui uma subcategoria. Por esse motivo a mesma n�o pode ser exclu�da");
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
	
	public Image getImage(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Image image = null;
		try{
			image = em.find(Image.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter image no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return image;
	}
	@SuppressWarnings("unchecked")
	public List<Image> getImages (Integer orderId){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Image> image = null;
		try{
			
			//TODO query para buscar imagens da order
			Query query = em.createQuery("FROM Image ORDER BY id");
			image = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter images no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return image;
	}

}
