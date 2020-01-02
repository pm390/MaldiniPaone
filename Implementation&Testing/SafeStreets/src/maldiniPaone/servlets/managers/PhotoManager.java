package maldiniPaone.servlets.managers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import maldiniPaone.constants.Constants;

public class PhotoManager 
{
	private PhotoManager() 
	{
		File file = new File(Constants.PHOTO_PATH);
		if(!file.exists()) file.mkdirs();
	}
	
	private static PhotoManager instance;
	
	public static PhotoManager getInstance()
	{
		return (instance==null)?instance=new PhotoManager():instance;
	}
	
	public BufferedImage getPhoto(String name) throws IOException
	{
		File foto=new File(Constants.PHOTO_PATH+File.separator+name);
		BufferedImage image=ImageIO.read(foto);
		return image;
	}
	
	public boolean savePhoto(String username,Integer assignmentId,Integer photoNumber,String fileExtension,InputStream photo) throws IOException
	{
		File foto= new File(Constants.PHOTO_PATH+File.separator+username+"-"
				+assignmentId+"-"+Integer.toString(photoNumber)+System.currentTimeMillis()+fileExtension);
		 foto.createNewFile();
		 Files.copy(photo, foto.toPath(), StandardCopyOption.REPLACE_EXISTING);
		 photo.close();
		return false;
	}
	
	public static void main(String[] args)
	{
		getInstance();
	}
}
