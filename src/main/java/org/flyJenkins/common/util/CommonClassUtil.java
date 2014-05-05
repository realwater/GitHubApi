package org.flyJenkins.common.util;

public class CommonClassUtil {

	/**
	 * 클래스 유무 체크 함수
	 * @param className
	 * @return
	 */
	public static boolean isClassExist(String className){		
	    boolean exist = true;
	    try {
	        Class.forName(className);
	    } catch (ClassNotFoundException e) {
	        exist = false;
	    }
	    return exist;		
	}
}
