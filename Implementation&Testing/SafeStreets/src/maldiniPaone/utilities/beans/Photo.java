package maldiniPaone.utilities.beans;

import java.io.InputStream;

public class Photo 
{
	
	
	
	
	
	
	
	
	
	private Integer assignmentId;
	private Integer photoNumber;
	private String fileExtension;
	private InputStream photo;
	











	
	private String name;
	
	
	public Photo() {}
	
	
	
	
	
	
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @return the reportId
	 */
	public Integer getAssignmentId() {
		return assignmentId;
	}
	/**
	 * @return the photoNumber
	 */
	public Integer getPhotoNumber() {
		return photoNumber;
	}
	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}
	/**
	 * @return the photo
	 */
	public InputStream getPhoto() {
		return photo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param reportId the reportId to set
	 */
	public void setAssignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
	}
	/**
	 * @param photoNumber the photoNumber to set
	 */
	public void setPhotoNumber(Integer photoNumber) {
		this.photoNumber = photoNumber;
	}
	/**
	 * @param fileExtension the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}
	
	
}
