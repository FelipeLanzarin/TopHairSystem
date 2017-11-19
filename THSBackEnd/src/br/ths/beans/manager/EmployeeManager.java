package br.ths.beans.manager;

import java.util.ArrayList;
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
	
	
	public static List<Employee> getEmployees() {
//		return getCidadeDao().getCidades();
		List<Employee> list = new ArrayList<>();
		Employee c = null;
		for(int i = 1; i<100 ; i++){
			c = new Employee();
			c.setId(i);
			c.setName("e"+i);
			c.setEmail("email"+i);
			c.setCpf("cpf"+i);
			c.setTelephone("telefone"+i);
			c.setAddress("rua"+i);
			c.setColor("#aaa");
			list.add(c);
		}
		
		return list;
	}
	
//	private static EmployeeDao getEmployeeDao(){
//		if(cd == null){
//			cd = new CidadeDao();
//		}
//		return cd;
//	}
}
