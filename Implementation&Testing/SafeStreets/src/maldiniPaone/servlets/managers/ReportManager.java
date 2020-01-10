package maldiniPaone.servlets.managers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import maldiniPaone.databaseConnection.DataAccessFacade;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.interfaces.ManageAssignment;
import maldiniPaone.servlets.managers.interfaces.ManageReportCreation;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.ViolationType;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Report;
import maldiniPaone.utilities.constants.Constants;
import maldiniPaone.utilities.beans.Photo;

/**
 * Implements {@link ManageReportCreation} and {@link ManageAssignment}.
 * Singleton design pattern
 **/
public class ReportManager implements ManageReportCreation, ManageAssignment {
	// ================================================================================
	// static fields
	// ================================================================================

	private static ReportManager instance;

	private ReportManager() {

	}

	// ================================================================================
	// Instantiator
	// ================================================================================
	/**
	 * Gets the instance of the ReportManager. Design pattern Singleton.
	 * 
	 * @return the instance
	 **/
	public static ReportManager getInstance() {
		return (instance == null) ? instance = new ReportManager() : instance;
	}

	// ================================================================================
	// ReportCreation adder
	// ================================================================================

	@Override
	public boolean addReport(String username, Location location, List<Photo> photos, String licensePlate, String note)
			throws ServerSideDatabaseException, IllegalParameterException, IOException {
		boolean result = false;
		Report report = new Report();
		report.setDate(new Timestamp(System.currentTimeMillis()));
		report.setLicensePlate(licensePlate);
		report.setLocation(location);
		report.setNote(note);
		report.setPhotos(photos);
		report.setUsername(username);

		try {
			final Assignment newlyCreatedAssignment = DataAccessFacade.getInstance().addNewReport(report);
			if (newlyCreatedAssignment != null) {
				int reportId=newlyCreatedAssignment.getReports().get(0).getId();
				// if needed get additional informations
				for (Photo temp : photos)// for each photo save it
				{
					try {
						String name=PhotoManager.getInstance().savePhoto(username, newlyCreatedAssignment.getId(),
								temp.getPhotoNumber(), temp.getFileExtension(), temp.getPhoto());
						DataAccessFacade.getInstance().addNewPhoto(name,reportId);
					} catch (Exception e) {
						if (Constants.VERBOSE) {
							System.err.println("photo couldn't be added");
							e.printStackTrace();
						}
						/* photo couldn't be added */
					}
				}
				result = true;
				Runnable nofityAssignment = new Runnable()// notifications are sent in a separated thread
				{
					public void run() {
						NotificationManager.getInstance().notify(newlyCreatedAssignment.getId(), location);
					}
				};
				nofityAssignment.run();// run a new thread to notify users when a new assignment is created
			}
		} catch (IllegalParameterException | ServerSideDatabaseException e) {
			throw e;
		}

		return result;
	}

	// ================================================================================
	// AssignmentServlet getter
	// ================================================================================

	@Override
	public List<Assignment> getAssignment(Location location)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().getAssignments(location);
	}
	// ================================================================================
	// AssignmentServlet change state
	// ================================================================================

	@Override
	public boolean refuseAssignment(Integer id, String username)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().updateAssignment(id, State.Pending, username, null);
	}

	@Override
	public boolean acceptAssignment(Integer id, String username)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().updateAssignment(id, State.Accepted, username, null);
	}

	@Override
	public boolean terminateAssignment(Integer id, String username, State finishState, ViolationType type)
			throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().updateAssignment(id, finishState, username, type);
	}

	// ================================================================================
	// AssignmentServlet check active
	// ================================================================================
	@Override
	public List<Integer> checkActive(String username) throws ServerSideDatabaseException, IllegalParameterException {
		return DataAccessFacade.getInstance().checkActive(username);
	}
}
