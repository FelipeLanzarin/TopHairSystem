package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.SubCategory;
import br.ths.database.SubCategoryDao;

public class SubCategoryManager {
	
	private static SubCategoryDao cd;
	
	public static Boolean update(SubCategory subCategory) {
		return Boolean.valueOf(getSubCategoryDao().updateSubCategory(subCategory));
	}

	public static Boolean create(SubCategory subCategory) {
		return Boolean.valueOf(getSubCategoryDao().createSubCategory(subCategory));
	}

	public static Boolean delete(SubCategory subCategory) {
		return Boolean.valueOf(getSubCategoryDao().deleteSubCategory(subCategory.getId()));
	}
	
	
	public static List<SubCategory> getSubCategories() {
		return getSubCategoryDao().getSubCategories();
	}
	
	
	
	private static SubCategoryDao getSubCategoryDao(){
		if(cd == null){
			cd = new SubCategoryDao();
		}
		return cd;
	}
//	
}
