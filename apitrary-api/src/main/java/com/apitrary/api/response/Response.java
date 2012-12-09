package com.apitrary.api.response;

public abstract class Response<T>{

	protected String result;
	protected String statusMessage;
	protected int statusCode;
	protected long responseTime;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
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

	@Override
	public String toString() {
		return "Response [result=" + result + ", statusMessage="
				+ statusMessage + ", statusCode=" + statusCode
				+ ", responseTime=" + responseTime + "]";
	}
}
