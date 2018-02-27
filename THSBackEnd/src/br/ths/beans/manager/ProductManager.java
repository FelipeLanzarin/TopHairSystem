package br.ths.beans.manager;

import java.text.DecimalFormat;
import java.util.List;

import br.ths.beans.Category;
import br.ths.beans.Product;
import br.ths.beans.SubCategory;
import br.ths.database.ProductDao;
import br.ths.exceptions.ManagersExceptions;

public class ProductManager {
	
	
	private static final DecimalFormat df = new DecimalFormat("###,###,##0.00");
	private static ProductDao cd;
	
	public static Boolean update(Product product) {
		return Boolean.valueOf(getProductDao().updateProduct(product));
	}

	public static Boolean create(Product product) throws ManagersExceptions {
		return Boolean.valueOf(getProductDao().createProduct(product));
	}

	public static Boolean delete(Product product) {
		return Boolean.valueOf(getProductDao().deleteProduct(product.getId()));
	}
	
	public static List<Product> getProducts() {
		return getProductDao().getProducts();
	}
	
	public static List<Product> getProductsBySubCategory(SubCategory subCategory) {
		return getProductDao().getProductsBySubCategoryId(subCategory.getId());
	}
	
	public static List<Product> getProductsByCategory(Category category) {
		return getProductDao().getProductsByCategoryId(category.getId());
	}
	
	public static Integer getNewProductId() {
		return getProductDao().getNewProductId();
	}
	
	public static String getUnitPriceAsString(Product product){
		if(product == null){
			return "0,00";
		}
		if(product.getPrice() != null){
			return df.format(product.getPrice());
		}
		return "0,00";
	}
	
	private static ProductDao getProductDao(){
		if(cd == null){
			cd = new ProductDao();
		}
		return cd;
	}

}
