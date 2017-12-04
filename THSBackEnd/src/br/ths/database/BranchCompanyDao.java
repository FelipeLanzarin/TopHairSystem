package br.ths.database;

import javax.persistence.EntityManager;

import br.ths.beans.BranchCompany;
import br.ths.tools.log.LogTools;

public class BranchCompanyDao {
	
	public BranchCompany getBranchCompany(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		BranchCompany branchCompany = null;
		try{
			branchCompany = em.find(BranchCompany.class, id);
		}catch (Exception e) {
			LogTools.logError("erro ao obter branchCompany no banco: "+ e.toString());
		}finally{
			em.close();
		}
		
		return branchCompany;
	}

}
