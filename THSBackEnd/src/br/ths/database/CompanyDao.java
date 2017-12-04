package br.ths.database;

import javax.persistence.EntityManager;

import br.ths.beans.Company;
import br.ths.tools.log.LogTools;

public class CompanyDao {
	
	public Company getCompany(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Company company = null;
		try{
			company = em.find(Company.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter company no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return company;
	}

}
