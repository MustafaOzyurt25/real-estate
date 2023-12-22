package com.realestate;


import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.realestate.service.*;
@SpringBootApplication
@RequiredArgsConstructor
public class RealEstateApplication implements CommandLineRunner {

	private final RoleService roleService;
	private final UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(RealEstateApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		if(roleService.getAllUserRoles().isEmpty())
		{
			roleService.saveRole(RoleType.ADMIN);
			roleService.saveRole(RoleType.CUSTOMER);
			roleService.saveRole(RoleType.MANAGER);
		}
		if(userService.getALlUsers().isEmpty()) {
			userService.saveDefaultAdmin(User.builder().build());
			userService.saveManagerForTest();
		}

	}
}