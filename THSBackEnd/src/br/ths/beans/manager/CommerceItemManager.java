package br.ths.beans.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
	private static final DecimalFormat df = new DecimalFormat("###,###,##0.00");
	
	public static Boolean update(CommerceItem commerceItem) throws ManagersExceptions {
		validateValues(commerceItem);
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
		validateValues(commerceItem);
		return Boolean.valueOf(getCommerceItemDao().createCommerceItem(commerceItem));
	}

	public static Boolean delete(CommerceItem commerceItem) {
		return Boolean.valueOf(getCommerceItemDao().deleteCommerceItem(commerceItem.getId()));
	}
	
	public static List<CommerceItem> getCommerceItems() {
		return getCommerceItemDao().getCommerceItems();
	}
	
	public static List<CommerceItem> getCommerceItemsByOrder(Order order ) {
		if(order == null){
			return null;
		}
		return getCommerceItemDao().getCommerceItemsByOrderId(order.getId());
	}
	
	public static String getUnitPriceAsString(CommerceItem commerceItem){
		if(commerceItem == null){
			return "0,00";
		}
		if(commerceItem.getUnitPrice() != null){
			return df.format(commerceItem.getUnitPrice());
		}
		return "0,00";
	}
	
	public static String getProductName(CommerceItem commerceItem){
		if(commerceItem == null){
			return "";
		}
		if(commerceItem.getProduct() != null){
			return commerceItem.getProduct().getName();
		}
		return "";
	}
	
	public static String getProductId(CommerceItem commerceItem){
		if(commerceItem == null){
			return "";
		}
		if(commerceItem.getProduct() != null){
			return commerceItem.getProduct().getId()+"";
		}
		return "";
	}
	
	public static String getAmountAsString(CommerceItem commerceItem){
		if(commerceItem == null){
			return "0,00";
		}
		if(commerceItem.getAmount() != null){
			return df.format(commerceItem.getAmount());
		}
		return "0,00";
	}
	
	public static String getDiscountAsString(CommerceItem commerceItem){
		if(commerceItem == null){
			return "0,00";
		}
		if(commerceItem.getDiscount() != null){
			return df.format(commerceItem.getDiscount());
		}
		return "0,00";
	}
	
	public static Double getValuePriceAsDouble(String value){
		if(value == null){
			return 0.0D;
		}
		try{
			return df.parse(value).doubleValue();
		}catch (Exception e) {
		}
		return 0.0D;
	}
	
	public static String getValuePriceAsString(Double value){
		if(value == null){
			return "0,00";
		}
		try{
			return df.format(value);
		}catch (Exception e) {
		}
		return "0,00";
	}
	
	public static Integer getQuantityAsInteger(String value){
		if(value == null){
			return 1;
		}
		try{
			return Integer.parseInt(value);
		}catch (Exception e) {
		}
		return 1;
	}
	
	public static CommerceItem addQuantityOnCommerceItem(CommerceItem ci, Integer quantity){
		if(ci == null || quantity == null){
			return null;
		}
		Integer newQuantity = quantity + ci.getQuantity();
		Double finalValue = arroudValue((ci.getUnitPrice() * newQuantity) - ci.getDiscount());
		ci.setQuantity(newQuantity);
		ci.setAmount(finalValue);
		return ci;
	}
	
	private static void validateValues(CommerceItem ci) throws ManagersExceptions{
		Double finalValue = arroudValue((ci.getUnitPrice()*ci.getQuantity()) - ci.getDiscount());
		int i2 = finalValue.compareTo(ci.getAmount());
		if(i2 != 0){
			ManagersExceptions me = new ManagersExceptions();
			me.setId(10);
			me.setExcepetionMessage("Valor de desconto e valor final não estão fechando!");
			throw me;
		}
	}
	
	public static Double arroudValue(Double value){
		if(value == null){
			return 0.0d;
		}
		BigDecimal vb = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return vb.doubleValue();
	}

	private static CommerceItemDao getCommerceItemDao(){
		if(cd == null){
			cd = new CommerceItemDao();
		}
		return cd;
	}

}
