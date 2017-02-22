package com.wangl;

import com.wangl.conf.MailConfig;
import com.wangl.modle.ServerObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试时,最好在测试类上加如下两个注解
 */
@RunWith(SpringRunner.class) //告诉Junit运行使用Spring 的单元测试支持；SpringRunner是SpringJunit4ClassRunner新的名称，只是视觉上看起来更简单了。
@SpringBootTest//该注解可以在一个测试类指定运行Spring Boot为基础的测试。
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	public ServerObj serverObj;

	@Test
	public void test1(){
		System.out.println(serverObj.toString());
	}

	@Autowired
	public MailConfig mailConfig;

	@Test
	public void test2(){
		System.out.println(mailConfig.toString());
	}

}
