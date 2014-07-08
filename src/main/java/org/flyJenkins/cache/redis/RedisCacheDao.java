package org.flyJenkins.cache.redis;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.flyJenkins.cache.DataCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisCacheDao implements DataCache {
	
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    
    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	private boolean isCacheActive = false; // 캐쉬 활성화 여부
	
	@PostConstruct
	public void setIsCacheActive() {
		System.out.println(redisTemplate+"realwater");
		if (redisTemplate != null) {
			this.isCacheActive = true;
		}
	}
	
	/**
	 *  redis 의 strings의 value 를 구함
	 */
	@Override
	public <T extends Serializable> T getValue(Serializable key) {
		T result = null;
		if (this.isCacheActive) {
			ValueOperations<Serializable, T> valueOper =(ValueOperations<Serializable, T>)  redisTemplate.opsForValue();		
			result = valueOper.get(key);
		}
		return result;
	}
	
	
	/**
	 * redis의 value 등록
	 * @param Serializable key
	 * @param Serializable value
	 */
	@Override
	public void setValue(Serializable key, Serializable value) {
		if (this.isCacheActive) {
			ValueOperations<Serializable, Serializable> valueOper = redisTemplate.opsForValue();
			valueOper.set(key, value);
		}
	}
	
	/**
	 * redis의 channel 삭제
	 */
	@Override
	public void delKey(Serializable key) {
		if (this.isCacheActive) {
			redisTemplate.delete(key);
		}
	}
	
	/**
	 * redis의 value expire 처리
	 */
	@Override
	public void setExpire(Serializable key, long second) {
		if (this.isCacheActive) {
			Date expireDate =  new Date();
			long expireTime = expireDate.getTime() + 1000 * second;
			expireDate.setTime(expireTime);
			redisTemplate.expireAt(key, expireDate);
		}
	}
}
