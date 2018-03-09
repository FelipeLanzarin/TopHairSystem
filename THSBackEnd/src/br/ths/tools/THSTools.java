package br.ths.tools;

import br.ths.beans.BranchCompany;
import br.ths.beans.Company;
import br.ths.beans.UserBranch;
import br.ths.configuration.StatusCompanyDao;
import br.ths.database.BranchCompanyDao;
import br.ths.database.CompanyDao;

public class THSTools {
	
	private static Company companySession;
	private static BranchCompany branchCompanySession;
	private static UserBranch userBranchSession;
	
	public static Company getCompanySession() {
		return companySession;
	}
	public static void setCompanySession(Company companySession) {
		THSTools.companySession = companySession;
	}
	public static BranchCompany getBranchCompanySession() {
		return branchCompanySession;
	}
	public static void setBranchCompanySession(BranchCompany branchCompanySession) {
		THSTools.branchCompanySession = branchCompanySession;
	}
	public static UserBranch getUserBranchSession() {
		return userBranchSession;
	}
	public static void setUserBranchSession(UserBranch userBranchSession) {
		THSTools.userBranchSession = userBranchSession;
	}
	public static void setConfSession(UserBranch userBranch){
		CompanyDao cd = new CompanyDao();
		setCompanySession(cd.getCompany(1));
		BranchCompanyDao cbd = new BranchCompanyDao();
		setBranchCompanySession(cbd.getBranchCompany(1));
		setUserBranchSession(userBranch);
	}
	public static Boolean verifyStatusCompany(Company company) throws Exception{
		Boolean status = true; //StatusCompanyDao.getStatus(company.getName());
		if(status == null){
			status = false;
		}
		return status;
	}
	
}
