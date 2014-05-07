package org.flyJenkins.cache.sevice;

import org.flyJenkins.cache.model.RedisCacheDto;
import org.junit.Before;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisDataServiceImpl implements RedisDataService {
	
	private RedisSerializer serializer;

	@Before
	public void setUp() {
		serializer = new JdkSerializationRedisSerializer();
	}

	@Override
	public void setSaveDataCache(RedisCacheDto redisCacheDto) {
		
	}

	@Override
	public Object getSaveDataCache(RedisCacheDto redisCacheDto) {
		return null;
	}
}