package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Profile;
import br.ths.database.ProfileDao;
import br.ths.exceptions.ManagersExceptions;

public class ProfileManager {
	
	private static ProfileDao dao;
	
	public static Boolean update(Profile profile) throws ManagersExceptions {
		if(profile.getCpf() != null && !profile.getCpf().isEmpty()){
			Profile p = getProfileByCpf(profile.getCpf());
			if(p != null){
				if(!p.getId().equals(profile.getId())){
					ManagersExceptions me = new ManagersExceptions();
					me.setId(ManagersExceptions.CPF_ALREADY_EXIST);
					me.setExcepetionMessage("O cpf informado já está sendo usado por outro usuário.");
					throw me;
				}
			}
		}
		return Boolean.valueOf(getProfileDao().updateProfile(profile));
	}

	public static Boolean create(Profile profile) throws ManagersExceptions {
		if(profile.getCpf() != null && !profile.getCpf().isEmpty()){
			Profile p = getProfileByCpf(profile.getCpf());
			if(p != null){
				ManagersExceptions me = new ManagersExceptions();
				me.setId(ManagersExceptions.CPF_ALREADY_EXIST);
				me.setExcepetionMessage("O cpf informado já está sendo usado por outro usuário!");
				throw me;
			}
		}
		return Boolean.valueOf(getProfileDao().createProfile(profile));
	}

	public static Boolean delete(Profile profile) {
		return Boolean.valueOf(getProfileDao().deleteProfile(profile.getId()));
	}
	
	public static List<Profile> getProfiles() {
		return getProfileDao().getProfiles();
	}
	
	public static Profile getProfileByCpf(String cpf){
		return getProfileDao().getProfileByCpf(cpf);
	}
	
	private static ProfileDao getProfileDao(){
		if(dao == null){
			dao = new ProfileDao();
		}	
		return dao;
	}

}
