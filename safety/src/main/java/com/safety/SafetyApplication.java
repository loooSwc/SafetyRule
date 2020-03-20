package com.safety;

import com.safety.common.dao.jpa.RepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.request.RequestContextListener;

@EntityScan("com.safety")
@SpringBootApplication(scanBasePackages = {"com.safety"},exclude = {MessageSourceAutoConfiguration.class, MultipartAutoConfiguration.class})
@ServletComponentScan("com.safety")
@EnableJpaRepositories(repositoryFactoryBeanClass = RepositoryFactoryBean.class, basePackages = {"com.safety"})
@EnableAspectJAutoProxy(exposeProxy = true)
public class SafetyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyApplication.class, args);
	}
	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}
}
