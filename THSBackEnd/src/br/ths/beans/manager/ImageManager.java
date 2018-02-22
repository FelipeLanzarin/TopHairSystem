package br.ths.beans.manager;

import java.io.File;
import java.util.List;

import br.ths.beans.Image;
import br.ths.beans.Order;
import br.ths.database.ImageDao;
import br.ths.exceptions.ManagersExceptions;
import br.ths.tools.log.LogTools;

public class ImageManager {
	
	private static ImageDao cd;
	
	public static Boolean update(Image image) {
		return Boolean.valueOf(getImageDao().updateImage(image));
	}

	public static Boolean create(Image image) {
		String orderId = image.getOrder().getId()+"";
		image.setPath("pictures/"+orderId+"/"+image.getPartOfProcess()+"/"+image.getDescription());
		return Boolean.valueOf(getImageDao().createImage(image));
	}

	public static Boolean delete(Image image) throws ManagersExceptions {
		if(Boolean.valueOf(getImageDao().deleteImage(image.getId()))){
			File file = new File(image.getPath());
    		if(!file.delete()){
    			LogTools.logError("Não foi possivel deletar o arquivo "+ image.getPath());
    		}
    		return true;
		}
		return false;
	}
	
	public static Boolean delete(List<Image> images) throws ManagersExceptions {
		if(Boolean.valueOf(getImageDao().deleteImage(images))){
			String path = "";
			for (Image image : images) {
				path = image.getPath();
				File file = new File(image.getPath());
				if(!file.delete()){
					LogTools.logError("Não foi possivel deletar o arquivo "+ image.getPath());
				}
			}
			if(path.equals("")){
				return true;
			}
			//remove o diretorio
			String[] split = path.split("/");
			int count = 0;
			String pathDelete = "";
			for (String string : split) {
				count++;
				if(count == 2){
					pathDelete+=string;
					break;
				}else{
					pathDelete+=string+"/";
				}
			}
			File file = new File(pathDelete);
			if (file.isDirectory()) {
				File[] sun = file.listFiles();
				for (File toDelete : sun) {
					if(!toDelete.delete()){
						LogTools.logError("Não foi possivel deletar o diretorio");
					}
				}
			}
			if(!file.delete()){
				LogTools.logError("Não foi possivel deletar o diretorio");
			}
    		return true;
		}
		return false;
	}
	
	public static List<Image> getImagesAfter(Order order) {
		return getImageDao().getImagesAfter(order.getId());
	}
	
	public static List<Image> getImagesBefore(Order order) {
		return getImageDao().getImagesBefore(order.getId());
	}
	
	public static List<Image> getImages(Order order) {
		return getImageDao().getImages(order.getId());
	}
	
	public static String setImagePath(Image image){
		if(image == null){
			return null;
		}
		String orderId = image.getOrder().getId()+"";
		String path = "pictures/"+orderId+"/"+image.getPartOfProcess()+"/";
		image.setPath(path+image.getDescription());
		return path;
	}
		
	private static ImageDao getImageDao(){
		if(cd == null){
			cd = new ImageDao();
		}
		return cd;
	}
//	
}
