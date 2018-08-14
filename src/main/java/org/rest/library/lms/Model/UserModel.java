package org.rest.library.lms.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserModel
{
	private int userID;
	private String userName;
	private int availBooks;
	private String userAddress;
	private int contactNumber;
	
	
	

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAvailBooks() {
		return availBooks;
	}
	public void setAvailBooks(int availBooks) {
		this.availBooks = availBooks;
	}
	
	
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public int getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}
	@Override
	public String toString() {
		return "UserModel [userID=" + userID + ", userName=" + userName + ", availBooks=" + availBooks
				+ ", userAddress=" + userAddress + ", contactNumber=" + contactNumber + "]";
	}
	
	
	
	

	
}
