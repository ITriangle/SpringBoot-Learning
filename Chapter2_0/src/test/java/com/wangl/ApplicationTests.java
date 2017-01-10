package com.wangl;

import com.wangl.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {


    @Test
    public void contextLoads() {
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws Exception {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));


    }



    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    /**
     * 序列化后,存入redis的会乱码,但是反序列化就可以了,对结果没有影响
     * 当然,汉字作为key值得时候也会出现乱码的情况,但是对判断结果也没有影响
     * @throws Exception
     */
    @Test
    public void testUser() throws Exception{
        User user = new User("1", 10);
        redisTemplate.opsForValue().set(user.getUsrname(), user);

        user = new User("2", 30);
        redisTemplate.opsForValue().set(user.getUsrname(), user);

        user = new User("3", 40);
        redisTemplate.opsForValue().set(user.getUsrname(), user);

        Assert.assertEquals(10, redisTemplate.opsForValue().get("1").getAge().longValue());
        Assert.assertEquals(30, redisTemplate.opsForValue().get("2").getAge().longValue());
        Assert.assertEquals(40, redisTemplate.opsForValue().get("3").getAge().longValue());
    }

}
