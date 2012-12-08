package com.apitrary.api.response;

import com.google.gson.JsonObject;

public abstract class Response<T>{

	protected JsonObject result;
//	"result": {
//    "a": "asd",
//    "_createdAt": "08 Dec 2012 13:03:41 +0000",
//    "c": "xcv",
//    "b": "wer",
//    "_updatedAt": "08 Dec 2012 13:03:41 +0000"
//},
	
	protected String statusMessage;
	protected int statusCode;
	protected long responseTime;
	
	public JsonObject getResult() {
		return result;
	}

	public void setResult(JsonObject result) {
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
