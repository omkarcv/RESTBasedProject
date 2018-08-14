package org.rest.library.lms.FileManager;

public class QueryConstants {
	public static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/lms_database?useSSL=false";
    public static final String DB_CONNECTION_USERNAME = "root";
    public static final String DB_CONNECTION_PASSWORD = "root";
    public static final String CLASS_FOR_NAME = "com.mysql.jdbc.Driver";
    
    public static final String SELECT_ALL_BOOKS  =  "select * from book";
    public static final String SELECT_BOOK  	=  "select * from book where bookID=?";
    public static final String TOTAL_BOOKS 	="select count(bookID) as TotalNumberOfBooks from book";
    public static final String INSERT_BOOK   =   "insert into book values(?,?,?,?,?,?)";
    public static final String UPDATE_BOOK	= "update book set bookName=?,bookAuthor=?,numberOfCopies=?,publication=?,authorID=? where bookID=?";
    public static final String DELETE_BOOK	="delete from book where bookID=?";
    public static final String INC_BOOK_COPY	= "update book set numberOfCopies=numberOfCopies-1 where bookID=?";
    public static final String DEC_BOOK_COPY	= "update book set numberOfCopies=numberOfCopies+1 where bookID=?";
    public static final String GET_NO_OF_COPIES	="select numberOfCopies from book where bookID=?";
    
    
    public static final String SELECT_ALL_USERS  =  "select * from user";
    public static final String SELECT_USER  =  "select * from user where userID=?";
    public static final String TOTAL_USERS	="select count(userID) as TotalNumberOfUsers from user";
    public static final String INSERT_USER 	= "insert into user values(?,?,?,?,?)";
    public static final String UPDATE_USER	="update user set userName=?, availBooks=?, userAddress=?, contactNumber=? where userID=?";
    public static final String DELETE_USER	="delete from user where userID=?";
    public static final String AVAIL_BOOKS	="select availBooks from user where userID=?";
    public static final String DEC_AVAIL_BOOKS	="update user set availBooks=availBooks-1 where userID=?";
    public static final	String INC_AVAIL_BOOKS	="update user set availBooks=availBooks+1 where userID=?";
    
    public static final String  ISSUE_STATUS="select * from issuestatus";
    public static final String  ISSUE_USER_STATUS="select * from issuestatus where userID=?";
    public static final String  ISSUE_BOOK_STATUS="select * from issuestatus where bookID=?";
    public static final String  INSERT_ISSUE_STATUS= "insert into issuestatus(userID,bookID) values(?,?)";
    public static final String  GET_ISSUE_STATUS="select * from issuestatus where userID= ? and BookId=?";
    public static final String DELETE_ISSUE_STATUS= "delete from issuestatus where userID=? and bookID =?";
    public static final String  CHECK_USER_STATUS="select * from issuestatus where userID=?";
    public static final String  CHECK_BOOK_STATUS="select * from issuestatus where bookID=?";
    
}
