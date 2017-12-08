package br.ths.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ths.beans.Employee;
import br.ths.tools.log.LogTools;



public class EmployeeDao {
	
	public boolean createEmployee (Employee employee){
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try{
			em.getTransaction().begin();
			em.persist(employee);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao inserir employee no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	public boolean updateEmployee(Employee employee){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(employee);
			em.getTransaction().commit();
		}catch (Exception e) {
			LogTools.logError("erro ao alterar employee no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	public boolean deleteEmployee(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Employee employee = getEmployee(id);
			if(employee != null){
				em.getTransaction().begin();
				employee = em.merge(employee);
				em.remove(employee);
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
	
	public Employee getEmployee(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Employee employee = null;
		try{
			employee = em.find(Employee.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter employee no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return employee;
	}
	@SuppressWarnings("unchecked")
	public List<Employee> getEmployees (){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Employee> employee = null;
		try{
			Query query = em.createQuery("FROM Employee ORDER BY name");
			employee = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter employees no banco: "+ e.toString());
		}finally{
			em.close();
		}
		return employee;
	}
	
	@SuppressWarnings("unchecked")
	public Employee getEmployeeByCpf(String cpf){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Employee> profile = null;
		try{
			Query query = em.createQuery("FROM Employee WHERE cpf = :cpf");
			query.setParameter("cpf", cpf);
			profile = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter profiles no banco: "+ e.toString());
		}finally{
			em.close();
		}
		try{
			return profile.get(0);
		}catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Employee getEmployeeByEmail(String email){
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<Employee> profile = null;
		try{
			Query query = em.createQuery("FROM Employee WHERE email = :email");
			query.setParameter("email", email);
			profile = query.getResultList();
		}catch (Exception e) {
			LogTools.logError("erro ao obter profiles no banco: "+ e.toString());
		}finally{
			em.close();
		}
		try{
			return profile.get(0);
		}catch (Exception e) {
			return null;
		}
	}

}
