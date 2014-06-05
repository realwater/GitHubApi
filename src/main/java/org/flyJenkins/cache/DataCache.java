package org.flyJenkins.cache;

import java.io.Serializable;

public interface DataCache {

	public <T extends Serializable> T getValue(Serializable key);
	
	public void setValue(Serializable key, Serializable value);
	
	public void delKey(Serializable key);
	
	public void setExpire(Serializable key, long second);
	
}