package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Employee;
import br.ths.database.EmployeeDao;

public class EmployeeManager {
	
	private static EmployeeDao dao;
	
	public static Boolean update(Employee employee) {
		return Boolean.valueOf(getEmployeeDao().updateEmployee(employee));
	}

	public static Boolean create(Employee employee) {
		return Boolean.valueOf(getEmployeeDao().createEmployee(employee));
	}

	public static Boolean delete(Employee employee) {
		return Boolean.valueOf(getEmployeeDao().deleteEmployee(employee.getId()));
	}
	
	public static List<Employee> getEmployees() {
		return getEmployeeDao().getEmployees();
		
	}
	
	private static EmployeeDao getEmployeeDao(){
		if(dao == null){
			dao = new EmployeeDao();
		}	
		return dao;
	}

}
