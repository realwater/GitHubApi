package org.flyJenkins.cache.redis;

import java.io.Serializable;
import java.util.Date;

import org.flyJenkins.cache.DataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisCacheDao implements DataCache {

	@Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	/**
	 *  redis 의 strings의 value 를 구함
	 */
	@Override
	public <T extends Serializable> T getValue(Serializable key) {
		@SuppressWarnings("unchecked")
		ValueOperations<Serializable, T> valueOper =(ValueOperations<Serializable, T>)  redisTemplate.opsForValue();
		T result = valueOper.get(key);
		return result;
	}
	
	
	/**
	 * redis의 value 등록
	 * @param Serializable key
	 * @param Serializable value
	 */
	@Override
	public void setValue(Serializable key, Serializable value) {
		ValueOperations<Serializable, Serializable> valueOper = redisTemplate.opsForValue();
		valueOper.set(key, value);
	}
	
	/**
	 * redis의 channel 삭제
	 */
	@Override
	public void delKey(Serializable key) {
		redisTemplate.delete(key);
	}
	
	/**
	 * redis의 value expire 처리
	 */
	@Override
	public void setExpire(Serializable key, long second) {
		Date expireDate =  new Date();
		long expireTime = expireDate.getTime() + 1000 * second;
		expireDate.setTime(expireTime);
		redisTemplate.expireAt(key, expireDate);
	}
}
