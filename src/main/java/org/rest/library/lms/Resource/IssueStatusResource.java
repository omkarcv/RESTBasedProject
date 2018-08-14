package org.rest.library.lms.Resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.rest.library.lms.Exception.DataNotFoundException;
import org.rest.library.lms.Exception.ErrorModel;
import org.rest.library.lms.Model.BookModel;
import org.rest.library.lms.Model.IssueStatusModel;
import org.rest.library.lms.Model.UserModel;
import org.rest.library.lms.Services.BookServices;
import org.rest.library.lms.Services.DataBaseServices;
import org.rest.library.lms.Services.IssueStatusServices;
import org.rest.library.lms.Services.UserServices;



@Path("issuestatus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IssueStatusResource {
	
	DataBaseServices dataBaseServices= DataBaseServices.getInstance();
	UserServices userServices = UserServices.getInstance();
	BookServices bookServices = BookServices.getInstance();
	IssueStatusServices issueStatusServices = IssueStatusServices.getInstance();
	ErrorModel errorModel= ErrorModel.getInstance();
	
	@GET
	public  List<IssueStatusModel> getStatus(){
		System.out.println("getStatus Called");
		return issueStatusServices.getStatus();	  
	}
	
	
	
	@GET
	@Path("user/{userid}")
	public Response getIssueStatusByUserId(@PathParam("userid") int userid)
	{
		System.out.println("getIssueStatusByUserId called");
		List<IssueStatusModel> issueStatusModel= issueStatusServices.getIssueStatusByUserId(userid);
		UserModel userModel= userServices.getParticularUser(userid);
		if(userModel.getUserID() !=0) {
			if(!issueStatusModel.isEmpty()) {
				if(issueStatusModel.get(0).getUserID() == 0)
				{
					throw new DataNotFoundException("No book is issued on userID : "+userid+" ,data not found,so no details available");
			
				}
				else
					return Response.status(Status.FOUND)
					.entity(issueStatusModel)
					.build();
			}
			else {
				throw new DataNotFoundException("no book is issued by user with userID "+userid);
			}
		}
		else {
			throw new DataNotFoundException("user with userID "+userModel.getUserID()+" is  not found in database");
		}
	}
	
	@GET
	@Path("book/{bookid}")
	public Response getIssueStatusByBookId(@PathParam("bookid") int bookid)
	{
		System.out.println("getIssueStatusByBookId called");
		List<IssueStatusModel> issueStatusModel= issueStatusServices.getIssueStatusByBookId(bookid);
		BookModel bookModel= bookServices.getParticularBook(bookid);
		if(bookModel.getBookID() !=0) {
			if(!issueStatusModel.isEmpty()) {
				if(issueStatusModel.get(0).getBookID() == 0)
				{
					throw new DataNotFoundException("No book is issued on bookID : "+bookid+" ,data not found, No details available");
			
				}
				else
					return Response.status(Status.FOUND)
					.entity(issueStatusModel)
					.build();
			}
			else {
				throw new DataNotFoundException("no book is issued by bookID "+bookid);
			}
		}
		else {
			throw new DataNotFoundException("book with bookID "+bookModel.getBookID()+" is  not found in database");
		}
	}
	
	
	@POST
	@Path("query")
	public Response addIssueEntry(@QueryParam("userID") int user_id,
											@QueryParam("bookID") int book_id)
	{
		System.out.println("addIssueEntry called");
		IssueStatusModel issueStatusModel=issueStatusServices.getIssueStatusOfIndividual(user_id, book_id);
		if(issueStatusModel.getUserID()!=0  &&  issueStatusModel.getBookID()!=0)
		{
			errorModel.setErrorCode(406);
			errorModel.setErrorMessage("UserID:"+user_id+" already issued book with bookID "+book_id);
			return Response.status(Status.NOT_ACCEPTABLE)
					.entity(errorModel)
					.build();
		}
		else {
				UserModel userModel= userServices.getParticularUser(user_id);
				BookModel bookModel= bookServices.getParticularBook(book_id);
				if(userModel.getUserID()==0) {
					throw new DataNotFoundException("User Not found with userID "+user_id+". Add User First.");
				}
				else {
						BookModel newBookModel =bookServices.getNumberOfCopies(book_id);
						System.out.println("number of copies for bookID="+book_id+" are:"+newBookModel.getNumberOfCopies());
						if(bookModel.getBookID()==0 || newBookModel.getNumberOfCopies()<=0)
						{
							throw new DataNotFoundException("Book Copy not available for bokID "+book_id);
						}
						else {
							int booksOfUser = userServices.getNumberOfBooksOfUser(user_id);
									if(booksOfUser<3) {
										IssueStatusModel issueStatusModel2=issueStatusServices.addIssueEntry(user_id, book_id);
										
										return Response.status(Status.CREATED)
												.entity(issueStatusModel2)
												.build();
									}
									else {
										errorModel.setErrorCode(406);
										errorModel.setErrorMessage("UserID:"+user_id+" already issued 3 books");
										return Response.status(Status.NOT_ACCEPTABLE)
												.entity(errorModel)
												.build();
										}
						}
					}
		}
				
}
	
	
	@DELETE
	@Path("query")
	public Response returnBook(@QueryParam("userID") int user_id,
											@QueryParam("bookID") int book_id)
	{
		System.out.println("returnBook called");
		IssueStatusModel issueStatusModel=issueStatusServices.getIssueStatusOfIndividual(user_id, book_id);
		if(issueStatusModel.getUserID()!=0 && issueStatusModel.getBookID() !=0) {
				IssueStatusModel issueStatusModel2=issueStatusServices.returnBook(user_id, book_id);
				
				return Response.status(Status.ACCEPTED)
						.entity(issueStatusModel2)
						.build();
		}
		else {
			throw new DataNotFoundException("Entry for User with userID "+user_id+ " and bookId "+book_id+" not found in database");
		}		
	}
	
	
	
	
	
	
	
}
