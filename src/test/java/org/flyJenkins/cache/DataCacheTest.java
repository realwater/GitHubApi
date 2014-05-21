package org.flyJenkins.cache;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*"})
public class DataCacheTest {

	@Autowired
    private RedisTemplate<String, String> redisTemplate;

	@Test
	public void test() {
		//redisTemplate.set(random, new Repos());
	}

}