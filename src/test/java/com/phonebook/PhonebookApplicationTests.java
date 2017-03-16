package com.phonebook;

import com.phonebook.dao.ContactDao;
import com.phonebook.dao.UserDao;
import com.phonebook.dao.impl.UserDaoImpl;
import com.phonebook.entity.Contact;
import com.phonebook.entity.User;
import com.phonebook.utils.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

@Autowired
UserDao udao;
@Autowired
ContactDao contactdao;

	@Test
	public void daoImpl() {
		//userDao
		String login = "TestuserLogin"+System.currentTimeMillis();
		String password = "TestUserPassword";
		String fio = "TestUserNSF";
		User user = new User(login, password,fio);
		udao.save(user);
		User byLogin = udao.findByLogin(login);
		User byId =  udao.findById(byLogin.getId());
		Assert.assertEquals(byLogin.getLogin(),byId.getLogin());
		Assert.assertEquals(byLogin.getPassword(),byId.getPassword());
		Assert.assertEquals(byLogin.getName(),byId.getName());
		Assert.assertEquals(fio,byId.getName());
		//ContactDao

		String surname = "cSurname";
		String name="cName";
		String fathername = "cfathername";
		String phonenumber = "+380954036366";
		String homenumber= "+380682929128";
		String adress = "contactAdress";
		String email= "test@test.com";
		Contact c = new Contact(name,surname,fathername,phonenumber,homenumber,adress,email,byId);
		contactdao.save(c);
		System.out.println(c.getId() + " CONTACTID");
		Contact byUser = contactdao.findByUser(byId.getId(),"", "","").get(0);
			Assert.assertEquals(byUser.getPhonenumber(), phonenumber);
		Contact c2 = new Contact(name,surname,fathername,"+380631111111",homenumber,adress,email,byId);
		System.out.println(c.getId() + " CONTACTID2");
		contactdao.save(c2);
		Contact sort = contactdao.findByUser(byLogin.getId(),"cNa","cSur","111").get(0);
			Assert.assertEquals(sort.getPhonenumber(), c2.getPhonenumber());
		List<Contact> contacts = contactdao.findByUser(user.getId(),"","","");
			Assert.assertEquals(contacts.get(0).getName(), contacts.get(1).getName());
			Assert.assertNotEquals(contacts.get(0).getPhonenumber(), contacts.get(1).getPhonenumber());
		contactdao.delete(sort.getId());
		int id = contactdao.findByUser(byId.getId(),"","","").size();
			Assert.assertEquals(1,id);
	}

	@Test
	public void contextLoads() {
	}

}
