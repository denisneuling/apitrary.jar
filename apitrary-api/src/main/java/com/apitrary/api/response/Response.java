package com.apitrary.api.response;

import org.json.JSONObject;


public abstract class Response<T>{

	private JSONObject result;
//	"result": {
//    "a": "asd",
//    "_createdAt": "08 Dec 2012 13:03:41 +0000",
//    "c": "xcv",
//    "b": "wer",
//    "_updatedAt": "08 Dec 2012 13:03:41 +0000"
//},
	
	private String statusMessage;
	private int statusCode;
	private long responseTime;
	
	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
}
