package org.flyJenkins.cache.sevice;

import org.flyJenkins.cache.model.RedisCacheDto;
import org.junit.Before;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public interface RedisDataService {

	public void setSaveDataCache(RedisCacheDto redisCacheDto);

	public Object getSaveDataCache(RedisCacheDto redisCacheDto);

}
