package br.ths.screens.main;

import br.ths.controllers.main.ControllerLogin;
import br.ths.database.EntityManagerUtil;

public class ManagerInitBD extends Thread{

	private ControllerLogin login;
	
	@Override
	public void run() {
		if(EntityManagerUtil.initEntityManager()){
			login.sucessConectBD();
		}else{
			login.failedConectBD();
		}
	}

	public ControllerLogin getLogin() {
		return login;
	}

	public void setLogin(ControllerLogin login) {
		this.login = login;
	}

	

}
