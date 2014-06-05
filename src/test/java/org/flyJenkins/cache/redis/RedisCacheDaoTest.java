package org.flyJenkins.cache.redis;


import java.io.Serializable;

import org.flyJenkins.cache.redis.model.RedisCacheDto;
import org.flyJenkins.github.model.ProjectDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class RedisCacheDaoTest {

	@Autowired
	private RedisCacheDao redisCacheDao; 
	
	//@Ignore
	@Test
	public void setTest() {
		redisCacheDao.delKey("testChannel");
		
		ProjectDto projectDto = new ProjectDto();		
		
		RedisCacheDto redisCacheDto = new RedisCacheDto(); 
		redisCacheDto.setChannelKey("testChannel");
		redisCacheDto.setValue(projectDto);
		redisCacheDto.setExpireTime(60);
		
		Serializable value = (Serializable) redisCacheDto.getValue();
		
		redisCacheDao.setValue(redisCacheDto.getChannelKey(), value);
	}
	
	//@Ignore
	@Test
	public void setExpireTest() {
		redisCacheDao.setExpire("testChannel", 10);
	}
	
	@Test
	public void getTest() {
		ProjectDto projectDto = redisCacheDao.getValue("testChannel");
		System.out.println(projectDto);
	}
	
	
}