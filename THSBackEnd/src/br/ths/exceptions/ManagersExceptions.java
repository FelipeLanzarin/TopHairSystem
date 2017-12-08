package br.ths.exceptions;

/**
 * Classe criada para retornar as mensagens de erro para o front
 */
public class ManagersExceptions extends Exception {
	
	private static final long serialVersionUID = 1L;
	public static final Integer CPF_ALREADY_EXIST = 100;
	public static final Integer ID_ALREADY_EXIST = 101;
	public static final Integer EMAIL_ALREADY_EXIST = 102;
	public static final Integer CITY_IN_USE = 103;
	
	private Integer id;
	private String excepetionMessage;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getExcepetionMessage() {
		return excepetionMessage;
	}
	public void setExcepetionMessage(String excepetionMessage) {
		this.excepetionMessage = excepetionMessage;
	}
	
	
}
