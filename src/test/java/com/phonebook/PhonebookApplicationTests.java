package com.phonebook;

import com.phonebook.entity.Contact;
import com.phonebook.entity.User;
import com.phonebook.utils.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("file:${lardi.conf}")
public class PhonebookApplicationTests {

	@Test
	public void validator() {
		//validate users
		Validator v = new Validator();
		String[] login = new String[]{"aa","asdf!","321645","фывафы"};
		String[] name = new String[]{"a","as","3216","фыва"};
		for (int i = 0; i < login.length; i++) {
			Assert.assertNotNull(v.checkUser("namee", login[i],"passs"));
			Assert.assertNotNull(v.checkUser(name[i], "qwerty","passs"));
			Assert.assertNotNull(v.checkUser("namee", "qwerty",name[i]));

		}
		Assert.assertNull(v.checkUser("namee", "qwe","passs"));

		//validate contacts
		String[] wrngPhones = new String[]{"0954123658", "380684545569", "+38055321654a", "asdfasdfsdf", "+380(68)3216549"};
		User user = new User("login", "password", "name");
		Contact c = new Contact("name","surname","fathername","+380954036366","+380664563289","adresssss","email", user);
		Assert.assertEquals("ok",v.checkContact(c));
		for (String p : wrngPhones){
			c.setPhonenumber(p);
			Assert.assertNotEquals("ok",v.checkContact(c));
		}
		c.setSurname("asd");
		Assert.assertNotEquals("ok",v.checkContact(c));
		c.setName("asd");
		Assert.assertNotEquals("ok",v.checkContact(c));
		c.setFathername("asd");
		Assert.assertNotEquals("ok",v.checkContact(c));



	}

	@Test
	public void contextLoads() {
	}

}
