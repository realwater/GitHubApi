package org.flyJenkins.cache.redis.model;

import java.io.Serializable;

public class RedisCacheDto implements Serializable {

	private static final long serialVersionUID = -8632784181851704124L;

	private String channelKey;
	
	private Object value;
	
	private long expireTime;

	public String getChannelKey() {
		return channelKey;
	}

	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
}
