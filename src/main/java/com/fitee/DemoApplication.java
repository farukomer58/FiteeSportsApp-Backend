package com.fitee;

import com.fitee.model.RoleEntity;
import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        TypedQuery<RoleEntity> customerRole = em.createQuery("SELECT r FROM RoleEntity r", RoleEntity.class);
        RoleEntity roleEntity = em.find(RoleEntity.class, 1l);
        log.info("All Customer with Customer Role : {}", roleEntity.getUsers());

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
