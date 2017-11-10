package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Employee;

public class EmployeeManager {
	
//	private static EmployeeDao cd;
	
	public static Boolean update(Employee employe) {
//		return Boolean.valueOf(getCidadeDao().updateCidade(cidade));
		return true;
	}

	public static Boolean create(Employee employe) {
//		return Boolean.valueOf(getCidadeDao().insereCidade(cidade));
		return true;
	}

	public static Boolean delete(Employee employe) {
//		return Boolean.valueOf(getCidadeDao().deleteCidade(cidade.getId()));
		return false;
	}
	
	
	public static List<Employee> getEmployee() {
//		return getCidadeDao().getCidades();
		return null;
	}
	
//	private static EmployeeDao getEmployeeDao(){
//		if(cd == null){
//			cd = new CidadeDao();
//		}
//		return cd;
//	}
}
