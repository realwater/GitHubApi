package org.flyJenkins.common.util;

public class CommonRegexUtil {

	/**
	 * 특수기호 제거 함수
	 * @param cont
	 * @return
	 */
	public static String getExcludePrefrenceCont(String cont){
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z.\\s]";
		cont = cont.replaceAll(match, "");
	    return cont;
	}

}
