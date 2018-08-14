package lms;


import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestUserResource {

	@Test
	public void postUserTest()
	{
		Response response= RestAssured.given().header("Content-Type", "application/json")
				.body("{\r\n" + 
						"    \"availBooks\": 0,\r\n" + 
						"    \"contactNumber\": 745896354,\r\n" + 
						"    \"userAddress\": \"umbraj\",\r\n" + 
						"    \"userID\": 7,\r\n" + 
						"    \"userName\": \"omkar\"\r\n" + 
						"}")
				.when().post("http://localhost:8080/lms/webapi/users");
		System.out.println(response.asString());
		int status= response.getStatusCode();
		Assert.assertEquals(201, status);
	}

	@Test
	public void getUserTest()
	{
		Response response= RestAssured.given().header("Content-Type", "application/json")
				.when().get("http://localhost:8080/lms/webapi/users");
		System.out.println(response.asString());
		int status= response.getStatusCode();
		Assert.assertEquals(200, status);
	}

//	@Test
//	public Response getSpecificUserTest()
//	{
//		return null;
//	}
//
//	@Test
//	public Response putUserTest()
//	{
//		return null;
//	}
//
//	@Test
//	public Response deleteUserTest()
//	{
//		return null;
////	}


}
