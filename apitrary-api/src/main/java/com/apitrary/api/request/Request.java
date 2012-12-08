package com.apitrary.api.request;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Request<T> implements Serializable{
	private static final long serialVersionUID = -5919354837987240483L;

	private Gson gson;
	protected Gson getGsonInstance(){
		if(gson==null){
			gson = new GsonBuilder().serializeNulls().create();
		}
        return gson;
	}
}
