package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.City;
import br.ths.database.CityDao;

public class CityManager {
	
	private static CityDao cd;
	
	public static Boolean update(City city) {
//		return Boolean.valueOf(getCityDao().updateCity(city));
		return true;
	}

	public static Boolean create(City city) {
//		return Boolean.valueOf(getCityDao().createCity(city));
		return true;
	}

	public static Boolean delete(City city) {
//		return Boolean.valueOf(getCityDao().deleteCity(city.getId()));
		return true;
	}
	
	
	public static List<City> getCities() {
		return getCityDao().getCities();
	}
	
	
	
	private static CityDao getCityDao(){
		if(cd == null){
			cd = new CityDao();
		}
		return cd;
	}
//	
}
