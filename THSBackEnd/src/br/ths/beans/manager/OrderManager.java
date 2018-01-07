package br.ths.beans.manager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.ths.beans.Order;
import br.ths.beans.Profile;
import br.ths.database.OrderDao;
import br.ths.exceptions.ManagersExceptions;


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
	private  static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static OrderDao cd;
	
	public static Boolean update(Order order) {
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
	
	public static String getAmountAsString(Order order){
		if(order == null){
			return "0,00";
		}
		if(order.getAmount() != null){
			return df.format(order.getAmount());
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
	
	public static String getFinalAmountAsString(Order order){
		if(order == null){
			return "0,00";
		}
		if(order.getFinalAmount() != null){
			return df.format(order.getFinalAmount());
		}
		return "0,00";
	}

	private static OrderDao getOrderDao(){
		if(cd == null){
			cd = new OrderDao();
		}
		return cd;
	}

}
