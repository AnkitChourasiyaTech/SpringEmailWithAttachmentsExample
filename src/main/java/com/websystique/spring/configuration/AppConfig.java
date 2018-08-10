package com.websystique.spring.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan(basePackages = "com.websystique.spring")
public class AppConfig {
	
	//Put Other Application configuration here.
	
	@Bean
	public JavaMailSender getMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		//Using gmail.
		mailSender.setHost("mail.wlmobiles.in.th");
		mailSender.setPort(587);
		mailSender.setUsername("test1@wlmobiles.in.th");
		mailSender.setPassword("@rRAcMrXna5");
		
		Properties javaMailProperties = new Properties();
		//javaMailProperties.put("mail.smtp.starttls.enable", "true");
		//javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");
		
		
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}
