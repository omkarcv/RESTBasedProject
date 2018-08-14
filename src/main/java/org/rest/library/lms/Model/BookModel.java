package org.rest.library.lms.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookModel 
{
	private int bookID;
	private String bookName;
	private String bookAuthor;
	private int numberOfCopies;
	private String publication;
	private int authorID;
	private String status;
	
	
	public String getBookName() 
	{
		return bookName;
	}
	
	public void setBookName(String bookName)
	{
		this.bookName  =  bookName;
	}
	public int getBookID() 
	{
		return bookID;
	}
	public void setBookID(int bookID) 
	{
		this.bookID  =  bookID;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor  =  bookAuthor;
	}
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies  =  numberOfCopies;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication  =  publication;
	}
	public int getAuthorID() {
		return authorID;
	}
	public void setAuthorID(int authorID) {
		this.authorID  =  authorID;
	}
	@Override
	public String toString() {
		return "BookModel [bookID = " + bookID + ", bookName = " + bookName + ", bookAuthor = " + bookAuthor
				+ ", numberOfCopies = " + numberOfCopies + ", publication = " + publication + ", authorID = " + authorID
				+ "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status  =  status;
	}
	

	
	 
}
