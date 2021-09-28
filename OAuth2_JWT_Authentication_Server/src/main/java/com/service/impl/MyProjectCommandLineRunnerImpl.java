package com.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.model.AccountKey;
import com.model.User;
import com.repository.UserRepository;
import com.service.AccountKeyService;
import com.service.MyProjectCommandLineRunnner;
import com.service.OneTimePasswordService;
import com.util.Util_ProxyServer;

@Component
public class MyProjectCommandLineRunnerImpl implements CommandLineRunner, MyProjectCommandLineRunnner{
	
	private UserRepository userRepository;
	private AccountKeyService accountKeyService;
	private OneTimePasswordService oneTimePasswordService;
	
	public MyProjectCommandLineRunnerImpl(UserRepository userRepository, AccountKeyService accountKeyService,
			OneTimePasswordService oneTimePasswordService, Util_ProxyServer proxyServer) {
		this.userRepository = userRepository;
		this.accountKeyService = accountKeyService;
		this.oneTimePasswordService = oneTimePasswordService;
	}

	public void run(String... args) throws Exception {
		dropUsersTable();
		createUsersTable();
		dropAccountKeyTable();
		createAccountKeyTable();
		dropOtpTable();
		createOtpTable();
		createDummyUser();
		createdummyAccountKey();
	}

	public void createUsersTable() {
		userRepository.createUsersTable();
	}

	public void dropUsersTable() {
		userRepository.dropUsersTable();
	}

	public void createDummyUser() {

		User user1 = new User();	
		user1.setId("bbf7f3f5-3d94-4ca9-9515-aafff26f9c8d");
		user1.setEmail("eu@fa.hu");
		user1.setPassword(new BCryptPasswordEncoder().encode("myPassword"));
		user1.setActive(true);
		user1.setMfa(false);
		userRepository.registerUser(user1, "user");
		//proxyServer.sendNewUserId(userRepository.findByEmail(user.getEmail()).getId());  
		
		User user2 = new User();
		user2.setId("884380f6-70cc-49f1-9135-532c3be6adda");
		user2.setEmail("admin@fa.hu");
	    user2.setPassword(new BCryptPasswordEncoder().encode("myAdmin"));
	    user2.setActive(true);
	    user2.setMfa(true);
		userRepository.registerUser(user2, "user admin");
		//proxyServer.sendNewUserId(userRepository.findByEmail(user2.getEmail()).getId()); 
		
		User user3 = new User();
		user3.setId("884380f6-70cc-49f1-9135-532c3be6adde");
		user3.setEmail("activationcode@test.hu");
		user3.setPassword(new BCryptPasswordEncoder().encode("myPassword2"));
		user3.setActive(false);
		user3.setMfa(true);
		userRepository.registerUser(user3, "user");
	}

	@Override
	public void createAccountKeyTable() {
		accountKeyService.createAccountKeyTable();	
	}

	@Override
	public void dropAccountKeyTable() {
		accountKeyService.dropAccountKeyTable();	
	}

	@Override
	public void createOtpTable() {
		oneTimePasswordService.createOneTimePasswordTable();
		
	}

	@Override
	public void dropOtpTable() {
		oneTimePasswordService.dropOneTimePasswordTable();
	}

	@Override
	public void createdummyAccountKey() {
		
		AccountKey accountKey = new AccountKey();
		accountKey.setAccountType("user");
		accountKey.setEmail("activationcode@test.hu");
		accountKey.setKey("key");
		
		accountKeyService.createAccountKey(accountKey);
	}
	
}
