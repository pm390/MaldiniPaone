package maldiniPaone.servlets.managers;

import java.sql.Timestamp;
import java.util.List;

import maldiniPaone.databaseConnection.DataAccessFacade;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.interfaces.ManageAssignment;
import maldiniPaone.servlets.managers.interfaces.ManageReportCreation;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Report;

public class ReportManager implements ManageReportCreation, ManageAssignment {
	
	
	
	
	//================================================================================
    // Report adder
    //================================================================================
	
	@Override
	public boolean addReport(String username, Location location, List<String> photos,String licensePlate,String note)
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		boolean result=false;
		Report report=new Report();
		report.setDate(new Timestamp(System.currentTimeMillis()));
		report.setLicensePlate(licensePlate);
		report.setLocation(location);
		report.setNote(note);
		report.setPhotoNames(photos);
		report.setUsername(username);
		Assignment newlyCreatedAssignment=null;
		try
		{
			newlyCreatedAssignment=DataAccessFacade.getInstance().addNewReport(report);
			if(newlyCreatedAssignment!=null)
			{
				result=true;
				Runnable nofityAssignment=new Runnable()
				{
					public void run() 
					{
						//NotificationManager.getIstance().notify(location);
						//TODO uncomment when developed
					}
				};
				nofityAssignment.run();//run a new thread to notify users when a new assignment is created
			}
		}
		catch(IllegalParameterException | ServerSideDatabaseException e)
		{
			throw e;
		}
		/*
		try
		{
			for(String photo: photos)
			{
				//TODO add photo 
			}
		}
		catch(IllegalParameterException | ServerSideDatabaseException e)
		{
			throw e;
		}
		*/
		
		
		return result;
	}

	//================================================================================
    // Assignment getter
    //================================================================================
	
	@Override
	public List<Assignment> getAssignment(Location location) throws ServerSideDatabaseException, IllegalParameterException
	{
		return DataAccessFacade.getInstance().getAssignments(location);
	}
	//================================================================================
    // Assignment change state
    //================================================================================
	
	@Override
	public boolean refuseAssignment(Integer id, String username) throws ServerSideDatabaseException, IllegalParameterException 
	{
		return DataAccessFacade.getInstance().updateAssignment(id, State.Pending, username);
	}

	@Override
	public boolean acceptAssignment(Integer id, String username)throws ServerSideDatabaseException, IllegalParameterException 
	{
		return DataAccessFacade.getInstance().updateAssignment(id, State.Accepted, username);
	}

	@Override
	public boolean terminateAssignment(Integer id, String username, State finishState) throws ServerSideDatabaseException, IllegalParameterException
	{
		return DataAccessFacade.getInstance().updateAssignment(id , finishState, username);
	}

}
