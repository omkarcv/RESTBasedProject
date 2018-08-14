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
import org.rest.library.lms.Model.IssueStatusModel;
import org.rest.library.lms.Model.UserModel;
import org.rest.library.lms.Services.DataBaseServices;
import org.rest.library.lms.Services.IssueStatusServices;
import org.rest.library.lms.Services.UserServices;




@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	
	
		DataBaseServices dataBaseServices= DataBaseServices.getInstance();
		UserServices userServices = UserServices.getInstance();
		IssueStatusServices issueStatusServices = IssueStatusServices.getInstance();
	
	    @GET
	    public List<UserModel> getUsers()
		{
			System.out.println("getUsersCalled");
			return userServices.getUsers();	    	
		}
	    
	    @GET
	    @Path("/{id}")
	    public Response getParticularUser(@PathParam("id") int userId)
		{
			System.out.println("getParticularuser Called");
			UserModel userModel=userServices.getParticularUser(userId);
			if(userModel.getUserID()==0) {
				throw new DataNotFoundException("User With id "+userId+" not found");
			}
			else
				return Response.status(Status.FOUND)
				.entity(userModel)
				.build();
	    } 
	    
	    
	    @GET
	    @Path("/numbers")
	    public int getTotalUsers() {
	    	System.out.println("getTotalUsers called"); 	
	    	return userServices.getTotalUsers();
	    	
	    }
	    
	   @POST
	   public Response addUser(UserModel userModel)
	   {
		   System.out.println("addUser called");
		   if(userServices.getParticularUser(userModel.getUserID()).getUserID()!=0)
		   {
			   ErrorModel errorModel =ErrorModel.getInstance();
				errorModel.setErrorCode(406);
				errorModel.setErrorMessage("Sorry but user with userID:"+userModel.getUserID()+" is already present in database");
				return Response.status(Status.NOT_ACCEPTABLE)
						.entity(errorModel)
						.build();
		   }
		   else {
			   UserModel newUserModel =  userServices.addUser(userModel);
			   if(newUserModel == null) {
				   return Response.status(Status.NO_CONTENT)
							.entity(newUserModel)
							.build();
			   }
			   else if(userModel.getUserName()==null || userModel.getUserID()==0|| userModel.getUserAddress()==null || userModel.getContactNumber()==0l)
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
						   .entity(userModel)
						   .build();
			   }
		   }
		   
	   }
	   
	   @PUT
	   public Response  updateUser(UserModel userModel)
	   {
		   System.out.println("updateUser Called");
		   UserModel userModel2=userServices.updateUser(userModel);
		   if(userModel2 == null) {
			   return Response.status(Status.METHOD_NOT_ALLOWED)
					   .entity(userModel2)
					   .build();
		   }
		   else {
			   if(userServices.getParticularUser(userModel.getUserID()).getUserID()==0) {
				   throw new DataNotFoundException("User with userID: "+userModel.getUserID()+ " is not found in Database");
			   }
			   else {
				   return Response.status(Status.OK)
						   .entity(userModel)
						   .build();
			   }
		   }
		   
	   }

	   
	   
	   @DELETE
	   @Path("/{userId}")
	   public Response deleteUser(@PathParam("userId") int id) {
		   System.out.println("deleteUser Called");
		  UserModel userModel=userServices.getParticularUser(id);
		  if(userModel.getUserID()!=0)
		  {
			  IssueStatusModel issueStatusModel=issueStatusServices.checkUserIssueStatus(id);
			  if(issueStatusModel.getUserID()==0) 
			  {
				  userServices.deleteUser(id);
				  return Response.status(Status.OK)
						  .entity(userModel)
						  .build();
			  }
			  else
			  {
				  ErrorModel errorModel =ErrorModel.getInstance();
					errorModel.setErrorCode(406);
					errorModel.setErrorMessage("User with userID="+id+" has issued books. cannot be deleted");
				   return Response.status(Status.NOT_ACCEPTABLE)
							.entity(errorModel)
							.build();
			  }
		  }
		  else
		  {
			  throw new DataNotFoundException("User with userID= "+(userModel.getUserID())+" is not found in Database");
		  }
	   }

}



