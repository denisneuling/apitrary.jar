/*
 * Copyright 2012-2013 Denis Neuling 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.apitrary.orm.core.marshalling.dumb;

import com.apitrary.orm.core.annotations.Codec;
import com.apitrary.orm.core.annotations.Column;
import com.apitrary.orm.core.annotations.Entity;
import com.apitrary.orm.core.annotations.Id;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
@Entity
public class DumbEntity{
	
	@Id
	private String id;
	
	@Column
	private String a;
	
	@Column
	@Codec(DumbCodec.class)
	private String b;
	
	public DumbEntity(){
	}
	
	public DumbEntity(String a, String b){
		this.a = a;
		this.b = b;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}