/**
 * 
 */
package com.apitrary.api.common.status;

/*
 * Copyright 2012 Denis Neuling 
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
import java.io.Serializable;

/**
 * <p>
 * Info class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public class Info implements Serializable {
	private static final long serialVersionUID = 4234844873408763205L;

	private String name;
	private String copyright;
	private String support;
	private String contact;
	private String version;
	private String company;

	/**
	 * <p>
	 * Constructor for Info.
	 * </p>
	 */
	public Info() {
	}

	/**
	 * <p>
	 * Getter for the field <code>name</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Setter for the field <code>name</code>.
	 * </p>
	 * 
	 * @param name
	 *            a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Getter for the field <code>copyright</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * <p>
	 * Setter for the field <code>copyright</code>.
	 * </p>
	 * 
	 * @param copyright
	 *            a {@link java.lang.String} object.
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * <p>
	 * Getter for the field <code>support</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getSupport() {
		return support;
	}

	/**
	 * <p>
	 * Setter for the field <code>support</code>.
	 * </p>
	 * 
	 * @param support
	 *            a {@link java.lang.String} object.
	 */
	public void setSupport(String support) {
		this.support = support;
	}

	/**
	 * <p>
	 * Getter for the field <code>contact</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * <p>
	 * Setter for the field <code>contact</code>.
	 * </p>
	 * 
	 * @param contact
	 *            a {@link java.lang.String} object.
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * <p>
	 * Getter for the field <code>version</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <p>
	 * Setter for the field <code>version</code>.
	 * </p>
	 * 
	 * @param version
	 *            a {@link java.lang.String} object.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * <p>
	 * Getter for the field <code>company</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * <p>
	 * Setter for the field <code>company</code>.
	 * </p>
	 * 
	 * @param company
	 *            a {@link java.lang.String} object.
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Info [name=" + name + ", copyright=" + copyright + ", support=" + support + ", contact=" + contact + ", version=" + version + ", company=" + company + "]";
	}
}
