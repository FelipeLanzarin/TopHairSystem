package br.ths.beans.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
		String orderId = image.getOrder().getId()+"";
		image.setPath("pictures/"+orderId+"/"+image.getPartOfProcess()+"/"+image.getDescription());
		return Boolean.valueOf(getImageDao().createImage(image));
	}

	public static Boolean delete(Image image) throws ManagersExceptions {
		return Boolean.valueOf(getImageDao().deleteImage(image.getId()));
	}
	
	public static List<Image> getImagesAfter(Order order) {
		return getImageDao().getImagesAfter(order.getId());
	}
	
	public static List<Image> getImagesBefore(Order order) {
		return getImageDao().getImagesBefore(order.getId());
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
