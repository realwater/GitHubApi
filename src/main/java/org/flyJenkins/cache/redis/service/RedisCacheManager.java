package org.flyJenkins.cache.redis.service;

import org.flyJenkins.cache.redis.model.RedisCacheDto;

public interface RedisCacheManager {

	/**
	 * Cache Data 저장
	 * @param redisCacheDto
	 */
	public void saveCacheValue(RedisCacheDto redisCacheDto);
	
	/**
	 * Cache Data 가져오기
	 * @param key
	 * @return
	 */
	public Object getCacheValue(String key);
	
	/**
	 * Cache Data 삭제
	 * @param key
	 */
	public void deleteCacheValue(String key);
}
