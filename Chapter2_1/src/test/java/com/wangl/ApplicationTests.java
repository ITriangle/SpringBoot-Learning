package com.wangl;

import com.wangl.domain.User;
import com.wangl.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional //开启事务,失败就回滚
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	/**
	 * 可以匹配到,能正确执行,可以修改修改idea配置Inspections，将spring 的severity的值设置为"warning"
	 */
	@Autowired
	private UserMapper userMapper;

	@Test
	@Rollback(true) //注解让每个单元测试都能在结束时回滚,所以数据库中不存在数据
	public void findByName() throws Exception {
		userMapper.insert("wangl",20);
		User user = userMapper.findByname("wangl");
		Assert.assertEquals(20,user.getAge().longValue());
	}




}
