package org.flyJenkins.cache.redis.service;

import org.flyJenkins.cache.redis.model.RedisCacheDto;
import org.flyJenkins.github.model.ProjectDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class RedisCacheManagerImplTest {
	
	@Autowired
	private RedisCacheManagerImpl redisCacheManagerImpl;

	@Test
	public void saveCacheValueTest() {
		ProjectDto projectDto = new ProjectDto();		
		
		RedisCacheDto redisCacheDto = new RedisCacheDto(); 
		redisCacheDto.setChannelKey("testChannel");
		redisCacheDto.setValue(projectDto);
		redisCacheDto.setExpireTime(60);
		
		
		redisCacheManagerImpl.saveCacheValue(redisCacheDto);
	}

	@Test
	public void getCacheValue() {
		redisCacheManagerImpl.getCacheValue("testChannel");
	}
}
