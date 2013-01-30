/**
 * 
 */
package com.apitrary.orm.core.util;

/**
 * @author ska
 *
 */
public class StringUtil {

	public String camelCase(String str){
		if(str != null && !str.isEmpty()){
			return str;
		}
		if(str.length()==1){
			return str.toUpperCase();
		}
		return str.substring(0, 1)+str.substring(1, str.length());
	}

	/**
	 * @param simpleName
	 * @return
	 */
	public static String toVerb(String string) {
		if(string == null){
			return string;
		}
		if(string.length() == 1){
			return string.toLowerCase();
		}
		return string.substring(0, 1).toLowerCase() + string.substring(1, string.length());
	}
}
