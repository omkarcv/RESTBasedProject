package org.rest.library.lms.Exception;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class ErrorModel {
	private String status;
	private String errorMessage;
	private int errorCode;
	
	static ErrorModel errorModel=new ErrorModel();
	private ErrorModel()
	{
		
	}
	public static ErrorModel getInstance()
	{
		return errorModel;
	}
	
	public ErrorModel(String errorMessage, int errorCode) {
		this.errorMessage=errorMessage;
		this.errorCode=errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String string) {
		this.status = string;
	}
	
	

}
