package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Profile;
import br.ths.database.ProfileDao;

public class ProfileManager {
	
	private static ProfileDao dao;
	
	public static Boolean update(Profile profile) {
		return Boolean.valueOf(getProfileDao().updateProfile(profile));
	}

	public static Boolean create(Profile profile) {
		return Boolean.valueOf(getProfileDao().createProfile(profile));
	}

	public static Boolean delete(Profile profile) {
		return Boolean.valueOf(getProfileDao().deleteProfile(profile.getId()));
	}
	
	public static List<Profile> getProfiles() {
		return getProfileDao().getProfiles();
		
	}
	
	private static ProfileDao getProfileDao(){
		if(dao == null){
			dao = new ProfileDao();
		}	
		return dao;
	}

}
