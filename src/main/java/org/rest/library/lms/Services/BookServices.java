package org.rest.library.lms.Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.rest.library.lms.FileManager.QueryConstants;
import org.rest.library.lms.Model.BookModel;

public class BookServices {
	static BookServices bookServices=new BookServices();
	DataBaseServices dataBaseServices= DataBaseServices.getInstance();
	IssueStatusServices issueStatussrvices = IssueStatusServices.getInstance();
	
	private BookServices() 
	{
		
	}
	public static BookServices getInstance()
	{
		return bookServices;
	}
	
	
	public List<BookModel> getBooks()
	{
		ArrayList<BookModel> books  =  new ArrayList<>();

		try
		{
			System.out.println("result in array: "+books);
			Statement statement=dataBaseServices.connection.createStatement();
			System.out.println("connection created");
			ResultSet resultset  =  statement.executeQuery(QueryConstants.SELECT_ALL_BOOKS);
			while(resultset.next())
			{
				BookModel bookModel= new BookModel();
				bookModel.setBookID(resultset.getInt(1));
				bookModel.setBookName(resultset.getString(2));
				bookModel.setBookAuthor(resultset.getString(3));
				bookModel.setNumberOfCopies(resultset.getInt(5));
				bookModel.setPublication(resultset.getString(4));
				bookModel.setAuthorID(resultset.getInt(6));
				books.add(bookModel);
				System.out.println(bookModel.toString());
				
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return books;
	}
	
	
    public BookModel getParticularBook(int bookId)
	{
		BookModel bookModel= new BookModel();

		try
		{
			PreparedStatement statement=dataBaseServices.connection.prepareStatement(QueryConstants.SELECT_BOOK);
			System.out.println("connection created");
			statement.setInt(1, bookId);
			ResultSet resultset  =  statement.executeQuery();
			if(resultset.next())
			{
				bookModel.setBookID(resultset.getInt(1));
				bookModel.setBookName(resultset.getString(2));
				bookModel.setBookAuthor(resultset.getString(3));
				bookModel.setNumberOfCopies(resultset.getInt(5));
				bookModel.setPublication(resultset.getString(4));
				bookModel.setAuthorID(resultset.getInt(6));
				
				System.out.println(bookModel.toString());
				
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return bookModel;
	} 
    
    public int getTotalBooks() {
    	System.out.println("getTotalBooks method called");
    	int totalNumberOfCopies=0;
    	try {
    		Statement statement=dataBaseServices.connection.createStatement();
    		ResultSet resultSet= statement.executeQuery(QueryConstants.TOTAL_BOOKS);
			if(resultSet.next()) {
				totalNumberOfCopies=  resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    	return totalNumberOfCopies;   	
    }
    
    public BookModel addBook(BookModel bookModel) {
		try
		{
			PreparedStatement statement   =   dataBaseServices.connection.prepareStatement(QueryConstants.INSERT_BOOK);
			statement.setInt(1, bookModel.getBookID());
			statement.setString(2, bookModel.getBookName());
			statement.setString(3, bookModel.getBookAuthor());
			statement.setInt(5, bookModel.getNumberOfCopies());
			statement.setString(4, bookModel.getPublication());
			statement.setInt(6, bookModel.getAuthorID());

			statement.executeUpdate();
			System.out.println("new Book Added ");			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}	
		return bookModel;
    }
    
    
    public BookModel updateBook(BookModel bookModel) 
    {
    	try
		{
			PreparedStatement statement   = dataBaseServices.connection.prepareStatement(QueryConstants.UPDATE_BOOK);
			statement.setString(1, bookModel.getBookName());
			statement.setString(2, bookModel.getBookAuthor());
			statement.setInt(3, bookModel.getNumberOfCopies());
			statement.setString(4, bookModel.getPublication());
			statement.setInt(5, bookModel.getAuthorID());
			statement.setInt(6, bookModel.getBookID());
			statement.executeUpdate();			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}    	
		return bookModel;    	
    } 
    
    
    public Response deleteBook(@PathParam("bookID") int bookId) 
    {
    	try {
			PreparedStatement preparedStatement= dataBaseServices.connection.prepareStatement(QueryConstants.DELETE_BOOK);
			preparedStatement.setInt(1, bookId);
			preparedStatement.executeUpdate();
			System.out.println("book entry with bookID ="+bookId+" is deleted");
			
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    	return null;
    } 
    
    public Response deductNumberOfCopiesOfBook(int book_id) 
    {
    	try
		{
			PreparedStatement statement   = dataBaseServices.connection.prepareStatement(QueryConstants.DEC_BOOK_COPY);
			statement.setInt(1, book_id);
			statement.executeUpdate();
			System.out.println("no of copies deducted by 1 for bookID="+book_id);			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}    	
		return null;    	
    }
    
    
    public Response addBookCopy(int book_id) 
    {
    	try
		{
			PreparedStatement statement   =	dataBaseServices.connection.prepareStatement(QueryConstants.INC_BOOK_COPY);
			statement.setInt(1, book_id);
			statement.executeUpdate();
			System.out.println("no of copies deducted by 1 for bookID="+book_id);			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}    	
		return null;    	
    }
   
    
    
    public BookModel getNumberOfCopies(int book_id) {
		System.out.println("getNumberOfCopies  method called");
		   BookModel bookModel=new BookModel();
		   try {			   
			   PreparedStatement statement= dataBaseServices.connection.prepareStatement(QueryConstants.GET_NO_OF_COPIES);
			   statement.setInt(1, book_id);
			   ResultSet resultSet=statement.executeQuery();
			   if(resultSet.next()) {
				   bookModel.setNumberOfCopies(resultSet.getInt(1));
				}				
		} catch (Exception e) {
			e.printStackTrace();		
		}
		   return bookModel;
	}
	
}
