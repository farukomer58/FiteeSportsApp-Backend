package com.fitee;

import com.fitee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.CommandLineRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Slf4j
@SpringBootApplication
public class DemoApplication  {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }




//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}
}
