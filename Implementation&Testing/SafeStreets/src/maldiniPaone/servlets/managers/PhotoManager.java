package maldiniPaone.servlets.managers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import maldiniPaone.utilities.constants.Constants;

/**
 * Manages the creation and retrieving of the photos
 **/
public class PhotoManager {
	// ================================================================================
	// Private constructor
	// ================================================================================
	private PhotoManager() {
		try {
			// creates the base directory
			File file = new File(Constants.PHOTO_PATH);
			if (!file.exists())
				file.mkdirs();
		} catch (Exception e) {
			System.err.println("Failed to load photo folder base");
		}
	}

	// ================================================================================
	// instance
	// ================================================================================
	private static PhotoManager instance;
	// ================================================================================
	// Instantiator
	// ================================================================================

	/**
	 * Gets the instance of the PhotoManager. Singleton design pattern
	 * 
	 * @return the instance
	 **/
	public static PhotoManager getInstance() {
		return (instance == null) ? instance = new PhotoManager() : instance;
	}

	/**
	 * Gets a photo in the form of BufferedImage
	 * 
	 * @param name : the name of the photo
	 * @return the photo's BufferedImage Object
	 * @throws IOException when the photo can't be accessed
	 **/
	public BufferedImage getPhoto(String name) throws IOException {
		BufferedImage image;
		try {
			File foto = new File(Constants.PHOTO_PATH + File.separator + name);
			 image = ImageIO.read(foto);
		} catch (IOException e) {
			System.err.println("failed to get photo");
			throw e;
		}
		return image;
	}

	/**
	 * Saves a photo in the system
	 * 
	 * @param username      the username of the user who adds the photo
	 * @param assignmentId  the id of the assignment to which the photo is
	 *                      associated
	 * @param photoNumber   the index of the photo which is added
	 * @param fileExtension the extension of the file
	 * @param photo         the InputStream corresponding to the photo to be saved
	 * @return the name of the photo
	 * @throws IOException when the photo can't be saved
	 **/
	public String savePhoto(String username, Integer assignmentId, Integer photoNumber, String fileExtension,
			InputStream photo) throws IOException {
		String res =username + "-" + assignmentId + "-"
				+ Integer.toString(photoNumber) + System.currentTimeMillis() + fileExtension ;
		try {
		String name = Constants.PHOTO_PATH + File.separator + username + "-" + assignmentId + "-"
					+ Integer.toString(photoNumber) + System.currentTimeMillis() + fileExtension;
			File foto = new File(name);
			foto.createNewFile();
			Files.copy(photo, foto.toPath(), StandardCopyOption.REPLACE_EXISTING);
			photo.close();

		} catch (IOException e) {
			System.err.println("failed to save photo");
			throw e;
		}
		return res;
	}

	public static void main(String[] args) {
		getInstance();
	}
}
