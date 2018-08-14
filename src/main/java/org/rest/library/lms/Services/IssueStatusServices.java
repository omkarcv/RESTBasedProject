package org.rest.library.lms.Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.rest.library.lms.FileManager.QueryConstants;
import org.rest.library.lms.Model.IssueStatusModel;

public class IssueStatusServices {

	DataBaseServices dataBaseServices= DataBaseServices.getInstance();
	UserServices userServices = UserServices.getInstance();
	BookServices bookServices = BookServices.getInstance();
	static IssueStatusServices issueStatusServices=new IssueStatusServices();
	
	
	private IssueStatusServices() 
	{
		
	}
	public static IssueStatusServices getInstance()
	{
		return issueStatusServices;
	}
	
	public  List<IssueStatusModel> getStatus(){
		System.out.println("getStatus called");
		List<IssueStatusModel> listOfIssueStatus  =  new ArrayList<>();
		try {
			Statement statement=dataBaseServices.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(QueryConstants.ISSUE_STATUS);
			while(resultSet.next())
			{
				IssueStatusModel issueStatusModel  = new IssueStatusModel();
				issueStatusModel.setUserID(resultSet.getInt(1));
				issueStatusModel.setBookID(resultSet.getInt(2));
				listOfIssueStatus.add(issueStatusModel);
				System.out.println(issueStatusModel);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return listOfIssueStatus;
	}
	
	public List<IssueStatusModel> getIssueStatusByUserId(int userid)
	{
		System.out.println("getIssueStatusByUserId called");
		List<IssueStatusModel> listOfIssueStatus = new ArrayList<>(); 
		try {
			PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.ISSUE_USER_STATUS);
			statement.setInt(1, userid);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next())
			{
				IssueStatusModel issueStatusModel =new IssueStatusModel();
				issueStatusModel.setUserID(resultSet.getInt(1));
				issueStatusModel.setBookID(resultSet.getInt(2));
				listOfIssueStatus.add(issueStatusModel);
				System.out.println(issueStatusModel);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return listOfIssueStatus;
	}
	
	
	public List<IssueStatusModel> getIssueStatusByBookId(int book_id)
	{
		System.out.println("getIssueStatusByBookId  method called");
		List<IssueStatusModel> listOfIssueStatus = new ArrayList<>(); 
		try {
			PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.ISSUE_BOOK_STATUS);
			statement.setInt(1, book_id);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next())
			{
				IssueStatusModel issueStatusModel =new IssueStatusModel();
				issueStatusModel.setUserID(resultSet.getInt(1));
				issueStatusModel.setBookID(resultSet.getInt(2));
				listOfIssueStatus.add(issueStatusModel);
				System.out.println(issueStatusModel);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return listOfIssueStatus;
	}
	
	
	public IssueStatusModel addIssueEntry(int userID,int bookID)
	{
		IssueStatusModel issueStatusModel1  = new IssueStatusModel();
		try {
			PreparedStatement statement = dataBaseServices.connection.prepareStatement(QueryConstants.INSERT_ISSUE_STATUS);
			statement.setInt(1, userID);
			statement.setInt(2, bookID);
			statement.executeUpdate();
			userServices.incrementAvailBook(userID);
			bookServices.deductNumberOfCopiesOfBook(bookID);
			issueStatusModel1.setBookID(bookID);
			issueStatusModel1.setUserID(userID);
			System.out.println("entry added for UserID="+userID+" and BookID"+bookID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return issueStatusModel1;
	}
	
	public IssueStatusModel getIssueStatusOfIndividual(int userId,int bookId)
	{
		System.out.println("getIssueStatusOfIndividual called");
		IssueStatusModel issueStatusModel =new IssueStatusModel();
		try {
			PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.GET_ISSUE_STATUS);
			statement.setInt(1, userId);
			statement.setInt(2, bookId);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next())
			{				
				issueStatusModel.setUserID(resultSet.getInt(1));
				issueStatusModel.setBookID(resultSet.getInt(2));
				System.out.println(issueStatusModel);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return issueStatusModel;
	}
	
	
	
	public IssueStatusModel returnBook(int user_id,int book_id)
		{
					IssueStatusModel issueStatusModel1  = new IssueStatusModel();
					try {
						PreparedStatement statement = dataBaseServices.connection.prepareStatement(QueryConstants.DELETE_ISSUE_STATUS);
						statement.setInt(1, user_id);
						statement.setInt(2, book_id);
						statement.executeUpdate();
						bookServices.addBookCopy(book_id);
						userServices.deductAvailBook(user_id);
						issueStatusModel1.setBookID(book_id);
						issueStatusModel1.setUserID(user_id);
						System.out.println("entry deleted for UserID="+user_id+" and BookID"+book_id);
					
					} catch (Exception e) {
						e.printStackTrace();
					}			
				return issueStatusModel1;		
		}
	
	
	
	public IssueStatusModel checkUserIssueStatus(int userId)
	{
		System.out.println("getUserIssueStatus called");
		IssueStatusModel issueStatusModel =new IssueStatusModel();
		try {
			
			PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.CHECK_USER_STATUS);
			statement.setInt(1, userId);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next())
			{				
				issueStatusModel.setUserID(resultSet.getInt(1));
				issueStatusModel.setBookID(resultSet.getInt(2));
				System.out.println(issueStatusModel);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return issueStatusModel;
	}
	
	
	
	
	public IssueStatusModel checkBookIssueStatus(int bookId)
	{
		System.out.println("getBookIssueStatus called");
		IssueStatusModel issueStatusModel =new IssueStatusModel();
		try {			
			PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.CHECK_BOOK_STATUS);
			statement.setInt(1, bookId);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next())
			{				
				issueStatusModel.setUserID(resultSet.getInt(1));
				issueStatusModel.setBookID(resultSet.getInt(2));
				System.out.println(issueStatusModel);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return issueStatusModel;
	}

}
