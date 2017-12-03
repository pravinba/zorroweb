package com.zorro;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zorro.backend.persistence.domain.backend.Role;
import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.backend.persistence.domain.backend.UserRole;
import com.zorro.backend.service.UserService;
import com.zorro.enums.PlansEnum;
import com.zorro.enums.RolesEnum;
import com.zorro.utils.UserUtils;
import com.zorro.web.i18n.I18NService;

@SpringBootApplication

public class ZorrowebApplication implements CommandLineRunner{

	@Value("${webmaster.username}")
	private String webMasterUsername;
	
	@Value("${webmaster.password}")
	private String webMasterPassword;
	
	@Value("${webmaster.email}")
	private String webMasterEmail;
	
	@Autowired
	UserService userService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ZorrowebApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ZorrowebApplication.class, args);
	}
	
	public void run(String... args) throws Exception{
		
		
		User user = UserUtils.createBasicUser(webMasterUsername,webMasterEmail);
		user.setPassword(webMasterPassword);
		
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, new Role(RolesEnum.ADMIN)));
		
		LOG.info("Creating Username :"+user.getUsername());		
		User newUser = userService.CreateUser(user, PlansEnum.PRO, userRoles);
		LOG.info("Created Username :"+user.getUsername());	
		
	}
}
