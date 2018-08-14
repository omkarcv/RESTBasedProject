package org.rest.library.lms.Resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.rest.library.lms.Exception.DataNotFoundException;
import org.rest.library.lms.Exception.ErrorModel;
import org.rest.library.lms.Model.BookModel;
import org.rest.library.lms.Model.IssueStatusModel;
import org.rest.library.lms.Services.BookServices;
import org.rest.library.lms.Services.DataBaseServices;
import org.rest.library.lms.Services.IssueStatusServices;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	
	BookServices bookServices= BookServices.getInstance();
	DataBaseServices dataBaseServices= DataBaseServices.getInstance();
	IssueStatusServices issueStatusServices = IssueStatusServices.getInstance();
	
    @GET
    public List<BookModel> getBooks()
	{
    	System.out.println("getBooksCalled");
		return bookServices.getBooks();
	}
    
    @GET
    @Path("/{id}")
    public Response getParticularBook(@PathParam("id") int bookId)
	{
    	System.out.println("getParticularBook Called");
		BookModel bookModel=bookServices.getParticularBook(bookId);
		if(bookModel.getBookID()==0) {
			throw new DataNotFoundException("Book With bookID "+bookId+" not found");
		}
		else
			return Response.status(Status.FOUND)
			.entity(bookModel)
			.build();
	}    

    
    
    
    @GET
    @Path("/numbers")
    public int getTotalBooks() {
    	System.out.println("getTotalBooks called"); 	
    	return bookServices.getTotalBooks();    	
    }

    
    
    
    @POST
    public Response addBook(BookModel bookModel) {
    	System.out.println("addBook called");
		   if(bookServices.getParticularBook(bookModel.getBookID()).getBookID()!=0)
		   {
			   ErrorModel errorModel =ErrorModel.getInstance();
				errorModel.setErrorCode(406);
				errorModel.setErrorMessage("Sorry but user with userID:"+bookModel.getBookID()+" is already present in database");
				return Response.status(Status.NOT_ACCEPTABLE)
						.entity(errorModel)
						.build();
		   }
		   else {
			   BookModel newBookModel =  bookServices.addBook(bookModel);
			   if(newBookModel == null) {
				   return Response.status(Status.NO_CONTENT)
							.entity(newBookModel)
							.build();
			   }
			   else if(bookModel.getBookName()==null || bookModel.getBookID()==0|| bookModel.getBookAuthor()==null || bookModel.getPublication()==null || bookModel.getAuthorID()==0 || bookModel.getNumberOfCopies()==0)
			   {
				   ErrorModel errorModel =ErrorModel.getInstance();
					errorModel.setErrorCode(406);
					errorModel.setErrorMessage("Data is not complete, entities should not be null and every entry must be present");
				   return Response.status(Status.NOT_ACCEPTABLE)
							.entity(errorModel)
							.build();
			   }
			   else
			   {
				   return Response.status(Status.CREATED)
						   .entity(bookModel)
						   .build();
			   }
		   }
    }
    
    
    @PUT
    public Response updateBook(BookModel bookModel) 
    {
    		System.out.println("updateBook Called");
		   BookModel bookModel1=bookServices.updateBook(bookModel);
		   if(bookModel1 == null) {
			   return Response.status(Status.METHOD_NOT_ALLOWED)
					   .entity(bookModel1)
					   .build();
		   }
		   else {
			   if(bookServices.getParticularBook(bookModel.getBookID()).getBookID()==0) {
				   throw new DataNotFoundException("Book with bookID: "+bookModel.getBookID()+ " is not found in Database");
			   }
			   else {
				   return Response.status(Status.OK)
						   .entity(bookModel)
						   .build();
			   }
		   }	
    } 
    
    
    @DELETE
    @Path("/{bookID}")
    public Response deleteBook(@PathParam("bookID") int id) {
    	System.out.println("deleteBook Called");
		  BookModel bookModel=bookServices.getParticularBook(id);
		  if(bookModel.getBookID()!=0)
		  {
			  IssueStatusModel issueStatusModel=issueStatusServices.checkBookIssueStatus(id);
			  if(issueStatusModel.getUserID()==0) 
			  {
				  bookServices.deleteBook(id);
				  return Response.status(Status.OK)
						  .entity(bookModel)
						  .build();
			  }
			  else
			  {
				  ErrorModel errorModel =ErrorModel.getInstance();
					errorModel.setErrorCode(406);
					errorModel.setErrorMessage("Book with bookID="+id+"  is issued to user. cannot be deleted");
				   return Response.status(Status.NOT_ACCEPTABLE)
							.entity(errorModel)
							.build();
			  }
		  }
		  else
		  {
			  throw new DataNotFoundException("User with userID= "+(bookModel.getBookID())+" is not found in Database");
		  }
	} 

}
