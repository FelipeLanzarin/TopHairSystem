package br.ths.beans.manager;

import java.util.ArrayList;
import java.util.List;

import br.ths.beans.City;

public class CityManager {
	
//	private static CidadeDao cd;
	
	public static Boolean update(City cidade) {
//		return Boolean.valueOf(getCidadeDao().updateCidade(cidade));
		return true;
	}

	public static Boolean create(City cidade) {
//		return Boolean.valueOf(getCidadeDao().insereCidade(cidade));
		return true;
	}

	public static Boolean delete(City cidade) {
//		return Boolean.valueOf(getCidadeDao().deleteCidade(cidade.getId()));
		return true;
	}
	
	
	public static List<City> getCities() {
//		return getCidadeDao().getCidades();
		List<City> list = new ArrayList<>();
		City c = null;
		for(int i = 1; i<10 ; i++){
			c = new City();
			c.setId(i);
			c.setName("cidade"+i);
			c.setCountry("pais"+i);
			c.setUf("UF"+i);
			list.add(c);
		}
		
		return list;
	}
	
	
	
//	private static CidadeDao getCidadeDao(){
//		if(cd == null){
//			cd = new CidadeDao();
//		}
//		return cd;
//	}
//	
}
