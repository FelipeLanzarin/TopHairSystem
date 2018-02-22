package br.ths.beans.manager;

import br.ths.beans.BranchCompany;
import br.ths.beans.Cashier;
import br.ths.database.CashierDao;
import br.ths.exceptions.ManagersExceptions;

public class CashierManager {
	
	private static CashierDao cd;
	
	public static Boolean update(Cashier cashier) {
		return Boolean.valueOf(getCashierDao().updateCashier(cashier));
	}

	public static Boolean create(Cashier cashier) {
		return Boolean.valueOf(getCashierDao().createCashier(cashier));
	}

	public static Boolean delete(Cashier cashier) throws ManagersExceptions {
		return Boolean.valueOf(getCashierDao().deleteCashier(cashier.getId()));
	}
	
	public static Cashier getCashier(Integer id) throws ManagersExceptions {
		return getCashierDao().getCashier(id);
	}
	
	public static Cashier getCashierByBranch(BranchCompany branchCompany) throws ManagersExceptions {
		if(branchCompany == null){
			return null;
		}
		return getCashierDao().getCashierByBranchId(branchCompany.getId());
	}
	
	
	private static CashierDao getCashierDao(){
		if(cd == null){
			cd = new CashierDao();
		}
		return cd;
	}
}
