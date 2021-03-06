package br.ths.beans.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ths.beans.CommerceItem;
import br.ths.beans.Image;
import br.ths.beans.Order;
import br.ths.beans.PaymentMethod;
import br.ths.beans.Profile;
import br.ths.database.OrderDao;
import br.ths.exceptions.ManagersExceptions;
import br.ths.utils.OrderStatus;


/**
 * 
 * Order � aberta com todos os valores zerados e j� � criada no banco de dados.
 * A cada nova adi��o de um commerceItem, todos os calculos j� ser�o refeitos no OrderManager e salvados no banco.
 * Se excluir a order, deve se excluir os commerceItens
 * 
 * @author Felipe Lazarin
 *
 */

public class OrderManager {
	
	private static final DecimalFormat df = new DecimalFormat("###,###,##0.00");
	private  static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private  static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
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
			order.setEmployee(EmployeeManager.getTheFirstEmployee());
		}
		return Boolean.valueOf(getOrderDao().createOrder(order));
	}

	public static Boolean delete(Order order) throws ManagersExceptions {
		if(orderIsOpen(order)){
			List<CommerceItem> cisOrder = CommerceItemManager.getCommerceItemsByOrder(order);//pega todos os commerceItens da order para deletar
			if(CommerceItemManager.delete(cisOrder)){
				List<Image> images = ImageManager.getImages(order);
				if(ImageManager.delete(images)){
					return Boolean.valueOf(getOrderDao().deleteOrder(order.getId()));
				}
				ManagersExceptions me = new ManagersExceptions();
				me.setId(18);
				me.setExcepetionMessage("Erro ao deletar imagens do pedido");
				throw me;
			}
			ManagersExceptions me = new ManagersExceptions();
			me.setId(18);
			me.setExcepetionMessage("Erro ao deletar os itens do pedido");
			throw me;
		}
		ManagersExceptions me = new ManagersExceptions();
		me.setId(18);
		me.setExcepetionMessage("Pedido n�o est� mais aberto. S� � poss�vel excluir pedidos abertos.");
		throw me;
	}
	
	public static List<Order> getOrders() {
		return getOrderDao().getOrders();
	}
	
	public static List<Order> getOrdersByDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    Date init = calendar.getTime();
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    Date finalDate = calendar.getTime();
		return getOrderDao().getOrdersByDate(init,finalDate);
	}
	
	public static List<Order> getOrdersByDate(Date start, Date finish){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.set(Calendar.MILLISECOND, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    Date init = calendar.getTime();
	    calendar.setTime(finish);
		calendar.set(Calendar.MILLISECOND, 9);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    Date finalDate = calendar.getTime();
		return getOrderDao().getOrdersByDate(init,finalDate);
	}
	
//	public static void main(String[] args) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		calendar.set(Calendar.MILLISECOND, 0);
//	    calendar.set(Calendar.SECOND, 0);
//	    calendar.set(Calendar.MINUTE, 0);
//	    calendar.set(Calendar.HOUR_OF_DAY, 0);
//	    calendar.add(Calendar.DAY_OF_MONTH, -7);
//	    Date init = calendar.getTime();
//	    calendar.setTime(new Date());
//		calendar.set(Calendar.MILLISECOND, 9);
//	    calendar.set(Calendar.SECOND, 59);
//	    calendar.set(Calendar.MINUTE, 59);
//	    calendar.set(Calendar.HOUR_OF_DAY, 23);
//	    Date finalDate = calendar.getTime();
//		System.out.println(getOrderDao().getSumOrderByDate(init, finalDate));
//		System.out.println(getOrderDao().getSumOrderPaiedByDate(init, finalDate));
//		List<Integer> ids = new ArrayList<Integer>();
//		ids.add(10000);
//		ids.add(10001);
//		ids.add(10002);
//		ids.add(10003);
//		ids.add(10004);
//		ids.add(10005);
//		ids.add(10006);
//		System.out.println(getOrderDao().getSumOrderByIds(ids));
//	}
	
	public static Double getSumOrderByDate(Date start, Date finish){
		start = adjustmentStartDate(start);
		finish = adjustmentEndDate(finish);
		Double sum = getOrderDao().getSumOrderByDate(start, finish);
		return roundValue(sum);
	}
	
	public static Double getSumOrderByIds(List<Integer> ids){
		if(ids.isEmpty()){
			return 0.0d;
		}
		Double sum = getOrderDao().getSumOrderByIds(ids);
		return roundValue(sum);
	}
	
	public static Double getSumOrderPaiedByIds(List<Integer> ids){
		if(ids.isEmpty()){
			return 0.0d;
		}
		Double sum = getOrderDao().getSumOrderPaiedByIds(ids);
		return roundValue(sum);
	}
	
	public static Double getSumOrderPaiedByDate(Date start, Date finish){
		start = adjustmentStartDate(start);
		finish = adjustmentEndDate(finish);
		Double sum = getOrderDao().getSumOrderPaiedByDate(start, finish);
		return roundValue(sum);
	}
	
	public static Double getSumOrderByDateAt(Date start, Date finish){
		start = adjustmentStartDate(start);
		finish = adjustmentEndDate(finish);
		Double sum = getOrderDao().getSumOrderByDateAt(start, finish);
		return roundValue(sum);
	}
	
	public static Double getSumOrderPaiedByDateAt(Date start, Date finish){
		start = adjustmentStartDate(start);
		finish = adjustmentEndDate(finish);
		Double sum = getOrderDao().getSumOrderPaiedByDateAt(start, finish);
		return roundValue(sum);
	}
	
	public static List<Order> getOrdersByCreationDate(Date start, Date finish){
	    Date init = adjustmentStartDate(start);
	    Date finalDate = adjustmentEndDate(finish);
		return getOrderDao().getOrdersByCreationDate(init,finalDate);
	}
	
	public static Date adjustmentStartDate(Date start){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.set(Calendar.MILLISECOND, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    return calendar.getTime();
	}
	
	public static Date adjustmentEndDate(Date finish){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(finish);
		calendar.set(Calendar.MILLISECOND, 9);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    return calendar.getTime();
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
	
	public static Boolean finishOrder(Order order) throws ManagersExceptions{
		if(order == null){
			return false;
		}
		if(canFinish(order)){
			order.setStatus(OrderStatus.CLOSED);
			order.setPaymentStatus(OrderStatus.PAYED);
			return update(order);
		}
		return false;
	}
	
	private static Boolean canFinish(Order order){
		if(order == null){
			return false;
		}
		Double valuePayed = 0.0d;
		for (PaymentMethod paymentMethod : PaymentManager.getPaymentMethodsByOrder(order)) {
			valuePayed +=paymentMethod.getAmount();
		}
		if(valuePayed.equals(order.getAmount())){
			return true;
		}
		return false;
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
	
	public static String getTimeAsString(Date date){
		String dateString = "";
		if(date == null){
			return null;
		}
		dateString=sdfTime.format(date);
		return dateString;
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
			String a = df.format(value);
			return a;
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
			me.setExcepetionMessage("Somat�ria dos produtos n�o fecha com o subtotal!");
			throw me;
		}
		Double finalValue = roundValue(subTotal - order.getDiscount());
		int i2 = finalValue.compareTo(order.getAmount());
		if(i2 != 0){
			ManagersExceptions me = new ManagersExceptions();
			me.setId(10);
			me.setExcepetionMessage("Valor de desconto e valor final n�o est�o fechando!");
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
	
	public static Boolean orderIsOpen(Order order){
		if("open".equals(order.getStatus())){
			return true;
		}
		return false;
	}
	
	public static Boolean orderIsClose(Order order){
		if("closed".equals(order.getStatus())){
			return true;
		}
		return false;
	}
	
	private static OrderDao getOrderDao(){
		if(cd == null){
			cd = new OrderDao();
		}
		return cd;
	}
	
}
