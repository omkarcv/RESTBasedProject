package org.rest.library.lms.Services;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.rest.library.lms.FileManager.QueryConstants;
import org.rest.library.lms.Model.UserModel;
import org.rest.library.lms.Services.DataBaseServices;


public class UserServices {
	
	
	static UserServices userServices=new UserServices();
	DataBaseServices dataBaseServices= DataBaseServices.getInstance();
	IssueStatusServices issueStatussrvices = IssueStatusServices.getInstance();
	private UserServices() 
	{
		
	}
	public static UserServices getInstance()
	{
		return userServices;
	}
	
	 public  List<UserModel> getUsers()
		{
		 System.out.println("getUsersCalled2");
			ArrayList<UserModel> users  =  new ArrayList<>();

			try
			{
				System.out.println("result in array: "+users);
				Statement statement  =  dataBaseServices.connection.createStatement();
				System.out.println("connection created");
				ResultSet resultset  =  statement.executeQuery(QueryConstants.SELECT_ALL_USERS);
				while(resultset.next())
				{
					UserModel userModel=new UserModel();
					userModel.setUserID(resultset.getInt(1));
					userModel.setUserName(resultset.getString(2));
					userModel.setAvailBooks(resultset.getInt(3));
					userModel.setUserAddress(resultset.getString(4));
					userModel.setContactNumber(resultset.getInt(5));
					
					users.add(userModel);
					System.out.println(userModel.toString());					
				}
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			return users;	    	
		}
	 
	 
	 public UserModel getParticularUser(int userId)
		{
			UserModel userModel= new UserModel();
			try
			{
				PreparedStatement statement  =  dataBaseServices.connection.prepareStatement(QueryConstants.SELECT_USER);	
				System.out.println("connection created");
				ResultSet resultset  =  statement.executeQuery();
				if(resultset.next())
				{
					
					userModel.setUserID(resultset.getInt(1));
					userModel.setUserName(resultset.getString(2));
					userModel.setAvailBooks(resultset.getInt(3));
					userModel.setUserAddress(resultset.getString(4));
					userModel.setContactNumber(resultset.getInt(5));

					System.out.println(userModel.toString());	
				}				
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			return userModel;
	    }
	 
	 
	 public int getTotalUsers() {
	    	System.out.println("getTotalUsers called");
	    	int totalNumberOfUsers=0;
	    	try { 		    	
	    		Statement statement=dataBaseServices.connection.createStatement();
	    		ResultSet resultSet= statement.executeQuery(QueryConstants.TOTAL_USERS);
				if(resultSet.next()) {
					totalNumberOfUsers=  resultSet.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	    	
	    	return totalNumberOfUsers;    	
	    }

	 
	 public UserModel addUser(UserModel userModel)
	   {
		   System.out.println("addUser called");
		   try {			  
			   PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.INSERT_USER);
			   statement.setInt(1, userModel.getUserID());
			   statement.setString(2, userModel.getUserName());
			   statement.setInt(3, userModel.getAvailBooks());
			   statement.setString(4, userModel.getUserAddress());
			   statement.setInt(5, userModel.getContactNumber());
			   
			   statement.executeUpdate();
			   System.out.println("new user added");
			
		   } catch (Exception e) {
			   	e.printStackTrace();
		   }
		   return userModel;		   
	   }
	 
	 
	 public UserModel  updateUser(UserModel userModel)
	   {
		   System.out.println("updateUser method Called");
		   try {
			   PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.UPDATE_USER);
			   statement.setString(1, userModel.getUserName() );
			   statement.setInt(2, userModel.getAvailBooks());
			   statement.setString(3, userModel.getUserAddress());
			   statement.setInt(4, userModel.getContactNumber());
			   statement.setInt(5, userModel.getUserID());
			   
			   statement.executeUpdate();
			   System.out.println("user info updated");
			
		   } catch (Exception e) {
			e.printStackTrace();
		   }	   
		   
		   return userModel;
	   }
	 
	 
	 public Response deleteUser( int id) {
		   System.out.println("deleteUser method Called");
		   try {		   
			   PreparedStatement statement= dataBaseServices.connection.prepareStatement(QueryConstants.DELETE_USER);
			   statement.setInt(1, id);			   
			   statement.executeUpdate();
			   System.out.println("user with id="+id+" deleted");
			
		   } catch (Exception e) {
			   e.printStackTrace();
		   }		   
		   return null;
	   }
	 
	 public int getNumberOfBooksOfUser(int user_id) {
			System.out.println("getNumberOfBooksOfUser called");
	    	int totalNumberOfBooks=0;
	    	try {
	    		System.out.println("inside try block of getNumberOfBooksOfUser method");
	    		PreparedStatement preparedStatement=dataBaseServices.connection.prepareStatement(QueryConstants.AVAIL_BOOKS);
	    		preparedStatement.setInt(1, user_id);
	    		ResultSet resultSet= preparedStatement.executeQuery();
				if(resultSet.next()) {
					totalNumberOfBooks=  resultSet.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}     	
	    	return totalNumberOfBooks;
		}
	 
	 public Response  deductAvailBook(int user_id)
	   {
		   System.out.println("deductBook Called");
		   try {
			   PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.DEC_AVAIL_BOOKS);			   
			   statement.setInt(1, user_id);			   
			   statement.executeUpdate();
			   System.out.println("user books updated");
			
		   } catch (Exception e) {
			e.printStackTrace();
		   }		   
		   return null;
	   }

	 public Response  incrementAvailBook(int user_id)
	   {
		   System.out.println("incrementAvailBook Called");
		   try {
			   PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.INC_AVAIL_BOOKS);			   
			   statement.setInt(1, user_id);			   
			   statement.executeUpdate();
			   System.out.println("user books updated");			
		   } catch (Exception e) {
			e.printStackTrace();
		   }		   
		   return null;
	   }
}
