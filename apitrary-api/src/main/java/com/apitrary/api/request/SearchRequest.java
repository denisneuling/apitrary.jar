package com.apitrary.api.request;

import com.apitrary.api.annotation.Method;
import com.apitrary.api.annotation.Path;
import com.apitrary.api.annotation.PathVariable;
import com.apitrary.api.annotation.Query;
import com.apitrary.api.annotation.Required;
import com.apitrary.api.common.HttpMethod;
import com.apitrary.api.response.SearchResponse;

@Method(HttpMethod.GET)
@Path("/${entity}/${id}") /** a/id || a?q= **/
public class SearchRequest extends Request<SearchResponse>{
	private static final long serialVersionUID = 7586680719718414828L;

	@Required
	@PathVariable("${entity}")
	private String entity;
	
	@Required
	@PathVariable("${id}")
	private String id;
	
	@Query("q")
	private String query;
	
	public SearchRequest(){
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
