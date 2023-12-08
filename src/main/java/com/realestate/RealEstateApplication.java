package com.realestate;


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
	public static void main(String[] args) {
		SpringApplication.run(RealEstateApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Merhaba burası RealEstateApplication class'ında \"run\" methodu");

		if(roleService.getAllUserRoles().isEmpty())
		{
			roleService.saveRole(RoleType.ADMIN);
			roleService.saveRole(RoleType.CUSTOMER);
			roleService.saveRole(RoleType.MANAGER);
		}

		// getUserRole() methodunun çalışığ çalışmadığı kontrol edildi. Çalışıyor.
		System.out.println("Customer Role = " + roleService.getRole(RoleType.CUSTOMER));
	}
}