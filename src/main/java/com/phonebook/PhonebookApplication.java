package com.phonebook;

import com.phonebook.dao.ContactDao;
import com.phonebook.dao.UserDao;
import com.phonebook.dao.impl.ContactDaoImpl;
import com.phonebook.dao.impl.UserDaoImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@PropertySource("file:${lardi.conf}")
public class PhonebookApplication{

	private String userFields = "id,login,password,name\n";
	private String contactsFields = "id,user_id,name,surname,fathername,phonenumber,homenumber,adress,email\n";

	@Bean
	public ErrorPageFilter errorPageFilter() {
		return new ErrorPageFilter();
	}

	@Bean
	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setEnabled(false);
		return filterRegistrationBean;
	}

	@Bean
	public ContactDao contactDao(){
		return new ContactDaoImpl();
	}

	@Bean
	public UserDao userDao(@Value("${spring.datasource.url}") String lardiPath) {
		if (lardiPath.startsWith("jdbc:relique:csv:")){
			String path = lardiPath.substring(17);
			if (!new File(path).exists()){
				new File(path).mkdirs();
			}
			try {
			if (!new File(path+"/user.csv").exists()){
				Files.write(Paths.get(path+"/user.csv"), userFields.getBytes());
			}
			if (!new File(path + "/contact.csv").exists()) {
				Files.write(Paths.get(path + "/contact.csv"), contactsFields.getBytes());
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new UserDaoImpl();
	}



	public static void main(String[] args) {

		SpringApplication.run(PhonebookApplication.class, args);
	}


}
