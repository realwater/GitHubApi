package org.flyJenkins.cache.redis.service;

import java.io.Serializable;

import org.flyJenkins.cache.redis.RedisCacheDao;
import org.flyJenkins.cache.redis.model.RedisCacheDto;

public class RedisCacheManagerImpl implements RedisCacheManager {
	
	private RedisCacheDao redisCacheDao;
	
	public void setRedisCacheDao(RedisCacheDao redisCacheDao) {
		this.redisCacheDao = redisCacheDao;
	}
	
	/**
	 * Cache Data 저장
	 * @param redisCacheDto
	 */
	@Override
	public void saveCacheValue(RedisCacheDto redisCacheDto) {
		Serializable value = (Serializable) redisCacheDto.getValue();
		
		// Data Cache 저장
		redisCacheDao.setValue(redisCacheDto.getChannelKey(), value);
		// Data Cache Expire Time 설정
		redisCacheDao.setExpire(redisCacheDto.getChannelKey(), redisCacheDto.getExpireTime());
	}
	
	/**
	 * Cache Data 가져오기
	 * @param key
	 * @return
	 */
	@Override
	public Object getCacheValue(String key) {
		return redisCacheDao.getValue(key);
	}

	/**
	 * Cache Data 삭제
	 */
	@Override
	public void deleteCacheValue(String key) {
		redisCacheDao.delKey(key);
	}

}
