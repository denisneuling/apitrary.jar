package com.apitrary.api.client;

public class ApitraryApi {

	private String apiId;
	private String apiKey;
	
	public ApitraryApi(String apiId, String apiKey){
		this.apiId = apiId;
		this.apiKey = apiKey;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}