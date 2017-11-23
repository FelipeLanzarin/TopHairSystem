package br.ths.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ths.tools.log.LogTools;


public class EntityManagerUtil {
	
	static EntityManagerFactory emf = null;

	public static EntityManager getEntityManager() {
		if (emf == null) {
			try {
				emf = Persistence.createEntityManagerFactory("thsbd");
			} catch (Exception arg0) {
				LogTools.logError(arg0);
			}
			if(emf == null){
				LogTools.logError(" ************** * EMF NULL *****************");
				return null;
			}
		}
		return emf.createEntityManager();
	}

	public static boolean initEntityManager() {
		if (emf == null) {
			try {
				emf = Persistence.createEntityManagerFactory("thsbd");
				LogTools.logDebug("Connection BD SUCESS");
			} catch (Exception arg0) {
				LogTools.logError(arg0);
				return false;
			}
		}
		return true;

	}

	public static void finalizefinalize(){
		if (emf != null) {
			try {
				emf.close();
			} catch (Exception arg1) {
				LogTools.logError(arg1);
			}
		}
	}
}