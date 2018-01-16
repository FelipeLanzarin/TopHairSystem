package br.ths.beans.manager;

import java.util.List;

import br.ths.beans.Image;
import br.ths.beans.Order;
import br.ths.database.ImageDao;
import br.ths.exceptions.ManagersExceptions;

public class ImageManager {
	
	private static ImageDao cd;
	
	public static Boolean update(Image image) {
		return Boolean.valueOf(getImageDao().updateImage(image));
	}

	public static Boolean create(Image image) {
		return Boolean.valueOf(getImageDao().createImage(image));
	}

	public static Boolean delete(Image image) throws ManagersExceptions {
		return Boolean.valueOf(getImageDao().deleteImage(image.getId()));
	}
	
	
	public static List<Image> getImages(Order order) {
		return getImageDao().getImages(order.getId());
	}
	
	
	
	private static ImageDao getImageDao(){
		if(cd == null){
			cd = new ImageDao();
		}
		return cd;
	}
//	
}
