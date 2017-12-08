package br.ths.beans.manager;

import java.security.MessageDigest;
import java.util.List;

import br.ths.beans.UserBranch;
import br.ths.database.UserBranchDao;

public class UserBranchManager {
	
	private static UserBranchDao cd;
	
	public static Boolean update(UserBranch userBranch) {
		userBranch.setPassword(getPasswordEncrypted(userBranch.getPassword()));
		return Boolean.valueOf(getUserBranchDao().updateUserBranch(userBranch));
	}

	public static Boolean create(UserBranch userBranch) {
		userBranch.setPassword(getPasswordEncrypted(userBranch.getPassword()));
		return Boolean.valueOf(getUserBranchDao().createUserBranch(userBranch));
	}

	public static Boolean delete(UserBranch userBranch) {
		return Boolean.valueOf(getUserBranchDao().deleteUserBranch(userBranch.getLogin()));
	}
	
	public static String getPasswordEncrypted(String password){
		if(password == null){
			return null;
		}
		String encrypetedPassword = "";
		try{
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
			 
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			encrypetedPassword = hexString.toString();

		}catch (Exception e) {
		}
		return encrypetedPassword;
	}
	
	public static List<UserBranch> getUserBranchs() {
		return getUserBranchDao().getUserBranchs();
	}
	
	private static UserBranchDao getUserBranchDao(){
		if(cd == null){
			cd = new UserBranchDao();
		}
		return cd;
	}

}
