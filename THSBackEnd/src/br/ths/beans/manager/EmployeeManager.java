package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Employee;
import br.ths.database.EmployeeDao;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.THSTools;

public class EmployeeManager {
	
	private static EmployeeDao dao;
	
	public static Boolean update(Employee employee) throws ManagersExceptions {
		if(employee.getCpf() != null && !employee.getCpf().isEmpty()){
			Employee p = getEmployeeByCpf(employee.getCpf());
			if(p != null){
				if(!p.getId().equals(employee.getId())){
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.CPF_ALREADY_EXIST);
					me.setExcepetionMessage("O cpf informado já está sendo usado por outro funcionário.");
					throw me;
				}
			}
		}
		if(employee.getEmail() != null && !employee.getEmail().isEmpty()){
			Employee p = getEmployeeByEmail(employee.getEmail());
			if(p != null){
				if(!p.getId().equals(employee.getId())){
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.EMAIL_ALREADY_EXIST);
					me.setExcepetionMessage("O email informado já está sendo usado por outro funcionário.");
					throw me;
				}
			}
		}
		return Boolean.valueOf(getEmployeeDao().updateEmployee(employee));
	}

	public static Boolean create(Employee employee) throws ManagersExceptions {
		if(employee.getCpf() != null && !employee.getCpf().isEmpty()){
			Employee p = getEmployeeByCpf(employee.getCpf());
			if(p != null){
				ManagersExceptions me = new ManagersExceptions();
				me.setId(ManagersExceptions.CPF_ALREADY_EXIST);
				me.setExcepetionMessage("O cpf informado já está sendo usado por outro funcionário.");
				throw me;
			}
		}
		if(employee.getEmail() != null && !employee.getEmail().isEmpty()){
			Employee p = getEmployeeByEmail(employee.getEmail());
			if(p != null){
				ManagersExceptions me = new ManagersExceptions();
				me.setId(ManagersExceptions.EMAIL_ALREADY_EXIST);
				me.setExcepetionMessage("O email informado já está sendo usado por outro funcionário.");
				throw me;
			}
		}
		employee.setCompany(THSTools.getCompanySession());
		employee.setBranchCompany(THSTools.getBranchCompanySession());
		return Boolean.valueOf(getEmployeeDao().createEmployee(employee));
	}

	public static Boolean delete(Employee employee) {
		return Boolean.valueOf(getEmployeeDao().deleteEmployee(employee.getId()));
	}
	
	public static List<Employee> getEmployees() {
		return getEmployeeDao().getEmployees();
		
	}
	
	public static List<Employee> getEmployeesActives() {
		return getEmployeeDao().getEmployeesActives();
		
	}
	
	public static Employee getTheFirstEmployee(){
		return getEmployeeDao().getEmployee(10000);
	}
	
	public static Employee getEmployeeByCpf(String cpf){
		return getEmployeeDao().getEmployeeByCpf(cpf);
	}
	
	public static Employee getEmployeeByEmail(String email){
		return getEmployeeDao().getEmployeeByEmail(email);
	}
	
	private static EmployeeDao getEmployeeDao(){
		if(dao == null){
			dao = new EmployeeDao();
		}	
		return dao;
	}

}
