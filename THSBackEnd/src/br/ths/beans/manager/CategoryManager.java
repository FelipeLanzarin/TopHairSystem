package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Category;
import br.ths.database.CategoryDao;
import br.ths.exceptions.ManagersExceptions;

public class CategoryManager {
	
	private static CategoryDao cd;
	
	public static Boolean update(Category category) {
		return Boolean.valueOf(getCategoryDao().updateCategory(category));
	}

	public static Boolean create(Category category) {
		return Boolean.valueOf(getCategoryDao().createCategory(category));
	}

	public static Boolean delete(Category category) throws ManagersExceptions {
		return Boolean.valueOf(getCategoryDao().deleteCategory(category.getId()));
	}
	
	
	public static List<Category> getCategories() {
		return getCategoryDao().getCategories();
	}
	
	
	
	private static CategoryDao getCategoryDao(){
		if(cd == null){
			cd = new CategoryDao();
		}
		return cd;
	}
//	
}
