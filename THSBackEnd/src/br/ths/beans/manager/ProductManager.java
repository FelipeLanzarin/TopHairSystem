package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Product;
import br.ths.database.ProductDao;
import br.ths.exceptions.ManagersExceptions;

public class ProductManager {
	
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
	
	public static Integer getNewProductId() {
		return getProductDao().getNewProductId();
	}
	
	private static ProductDao getProductDao(){
		if(cd == null){
			cd = new ProductDao();
		}
		return cd;
	}

}
