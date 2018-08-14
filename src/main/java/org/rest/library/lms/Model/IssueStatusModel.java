package org.rest.library.lms.Model;

/**
 * @author Omieee
 *
 */
public class IssueStatusModel {
	private int userID;
	private int bookID;
	
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	@Override
	public String toString() {
		return "IssueStatusModel [userID=" + userID + ", bookID=" + bookID + "]";
	}

}
