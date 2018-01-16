package br.ths.beans.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.ths.beans.CommerceItem;
import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.database.OrderDao;
import br.ths.exceptions.ManagersExceptions;


/**
 * 
 * Order é aberta com todos os valores zerados e já é criada no banco de dados.
 * A cada nova adição de um commerceItem, todos os calculos já serão refeitos no OrderManager e salvados no banco.
 * Se excluir a order, deve se excluir os commerceItens
 * 
 * @author Felipe Lazarin
 *
 */

public class OrderManager {
	
	private static final DecimalFormat df = new DecimalFormat("###,###,##0.00");
	private  static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static OrderDao cd;
	
	public static Boolean update(Order order) throws ManagersExceptions {
		validateValues(order);
		return Boolean.valueOf(getOrderDao().updateOrder(order));
	}
	
	/**
	 * Cria pedido para o cliente enviado por parametro
	 * @param profile
	 * @return
	 * @throws ManagersExceptions
	 */
	public static Order createForProfile(Profile profile) throws ManagersExceptions {
		Order order = new Order();
		order.setProfile(profile);
		if(create(order)){
			return order;
		}
		return null;
	}
	
	public static Boolean create(Order order) throws ManagersExceptions {
		if(order.getEmployee() == null){
			//TODO get the first employee
		}
		return Boolean.valueOf(getOrderDao().createOrder(order));
	}

	public static Boolean delete(Order order) {
		return Boolean.valueOf(getOrderDao().deleteOrder(order.getId()));
	}
	
	public static List<Order> getOrders() {
		return getOrderDao().getOrders();
	}
	
	public static List<Order> getOrdersByProfile(Profile profile) {
		if(profile == null){
			return null;
		}
		return getOrderDao().getOrdersByProfileId(profile.getId());
	}
	
	public static Order recalculateOrder(Order order) throws ManagersExceptions{
		order.setSubTotalAmount(getSubTotal(order));
		Double amount = roundValue(order.getSubTotalAmount() - order.getDiscount());
		order.setAmount(amount);
		update(order);
		return order;
	}
	
	private static Double getSubTotal(Order order){
		List<CommerceItem> listCommerceItemns = CommerceItemManager.getCommerceItemsByOrder(order);
		Double value =  0.0D;
		if(listCommerceItemns != null){
			for (CommerceItem commerceItem : listCommerceItemns) {
				value+=commerceItem.getAmount();
				value = roundValue(value);
			}
		}
		return value;
	}
	
	public static String getStatusAsString(Order order){
		if(order == null){
			return "";
		}
		if(order.getStatus() == null){
			return "";
		}
		switch (order.getStatus()) {
		case "open":
			return "Esperando Atendimento";
		case "attended":
			return "Atendido";
		case "closed":
			return "Finalizado";
		default:
			break;
		}
		return "";
	}
	
	public static String getCreationDateAsString(Order order){
		String date = "";
		if(order == null){
			return date;
		}
		Date creationDate = order.getCreationDate();
		if(creationDate != null){
			date=sdf.format(creationDate);
		}
		return date;
	}
	
	public static String getProfileAsString(Order order){
		String name = "";
		if(order == null){
			return name;
		}
		Profile profile = order.getProfile();
		if(profile != null){
			name=profile.getName();
		}
		return name;
	}
	
	public static String getSchedulerAsString(Order order){
		String date = "";
		if(order == null){
			return date;
		}
		Date scheduler = order.getScheduler();
		if(scheduler != null){
			date=sdf.format(scheduler);
		}
		return date;
	}
	
	public static String getSubTotalAmountAsString(Order order){
		if(order == null){
			return "0,00";
		}
		if(order.getSubTotalAmount() != null){
			return df.format(order.getSubTotalAmount());
		}
		return "0,00";
	}
	
	public static String getDiscountAsString(Order order){
		if(order == null){
			return "0,00";
		}
		if(order.getDiscount() != null){
			return df.format(order.getDiscount());
		}
		return "0,00";
	}
	
	public static String getAmountAsString(Order order){
		if(order == null){
			return "0,00";
		}
		if(order.getAmount() != null){
			return df.format(order.getAmount());
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
	
	private static void validateValues(Order order) throws ManagersExceptions{
		if(order == null){
			return;
		}
		Double subTotal = getSumAllCommerceItems(order);
		int c1 = subTotal.compareTo(order.getSubTotalAmount());
		if(c1 != 0){
			ManagersExceptions me = new ManagersExceptions();
			me.setId(10);
			me.setExcepetionMessage("Somatória dos produtos não fecha com o subtotal!");
			throw me;
		}
		Double finalValue = roundValue(subTotal - order.getDiscount());
		int i2 = finalValue.compareTo(order.getAmount());
		if(i2 != 0){
			ManagersExceptions me = new ManagersExceptions();
			me.setId(10);
			me.setExcepetionMessage("Valor de desconto e valor final não estão fechando!");
			throw me;
		}
	}
	
	private static Double getSumAllCommerceItems(Order order){
		Double sum = 0.0D;
		if(order == null){
			return sum;
		}
		
		List<CommerceItem> list = CommerceItemManager.getCommerceItemsByOrder(order);
		for (CommerceItem commerceItem : list) {
			sum += commerceItem.getAmount();
			sum = roundValue(sum);
		}
		return sum;
	}
	
	public static Double roundValue(Double value){
		if(value == null){
			return 0.0d;
		}
		BigDecimal vb = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return vb.doubleValue();
	}
	
	private static OrderDao getOrderDao(){
		if(cd == null){
			cd = new OrderDao();
		}
		return cd;
	}
	
	

}
