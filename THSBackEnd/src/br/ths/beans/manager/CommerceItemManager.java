package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.CommerceItem;
import br.ths.beans.Order;
import br.ths.database.CommerceItemDao;
import br.ths.exceptions.ManagersExceptions;


/**
 * 
 * @author Felipe Lazarin
 *
 */

public class CommerceItemManager {
	
	private static CommerceItemDao cd;
	
	public static Boolean update(CommerceItem commerceItem) {
		return Boolean.valueOf(getCommerceItemDao().updateCommerceItem(commerceItem));
	}
	
	/**
	 * Cria pedido para o cliente enviado por parametro
	 * @param profile
	 * @return
	 * @throws ManagersExceptions
	 */
	public static CommerceItem create(Order order) throws ManagersExceptions {
		CommerceItem commerceItem = new CommerceItem();
		commerceItem.setOrder(order);
		if(create(commerceItem)){
			return commerceItem;
		}
		return null;
	}
	
	public static Boolean create(CommerceItem commerceItem) throws ManagersExceptions {
		return Boolean.valueOf(getCommerceItemDao().createCommerceItem(commerceItem));
	}

	public static Boolean delete(CommerceItem commerceItem) {
		return Boolean.valueOf(getCommerceItemDao().deleteCommerceItem(commerceItem.getId()));
	}
	
	public static List<CommerceItem> getCommerceItems() {
		return getCommerceItemDao().getCommerceItems();
	}
	
	public static List<CommerceItem> getCommerceItemsByProfile(Order order ) {
		if(order == null){
			return null;
		}
		return getCommerceItemDao().getCommerceItemsByOrderId(order.getId());
	}

	private static CommerceItemDao getCommerceItemDao(){
		if(cd == null){
			cd = new CommerceItemDao();
		}
		return cd;
	}

}
