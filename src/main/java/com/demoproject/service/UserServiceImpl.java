package com.demoproject.service;

import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demoproject.entity.Role;
import com.demoproject.entity.User;
import com.demoproject.repository.RoleRepository;
import com.demoproject.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String key;
	
	private String adminKey; 

	private UserRepository userRepository;
	
	private RoleRepository roleRepository;
	
	private EmailService emailService;

	private final String USER_ROLE = "ROLE_USER";
	
	private final String ADMIN_ROLE = "ROLE_ADMIN";
	
	@Autowired
	public void setEmailservice(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public String registerUser(User userToRegister,Locale locale) {
		User userCheck = userRepository.findByEmail(userToRegister.getEmail());

		if (userCheck != null)
			return "alreadyExists";

		Role userRole = roleRepository.findByRole(USER_ROLE);
		if (userRole != null) {
			userToRegister.getRoles().add(userRole);
		} else {
			userToRegister.addRoles(USER_ROLE);
		}
		userToRegister.setPassword(new BCryptPasswordEncoder().encode(userToRegister.getPassword()));
		userToRegister.setEnabled(false);
		userToRegister.setActivation(generateKey());
		key = userToRegister.getActivation();
		userRepository.save(userToRegister);
		
		if(String.valueOf(locale.getLanguage()).equals("en")) {
			emailService.sendMessageen(userToRegister.getEmail(),userToRegister.getFirstName(),userToRegister.getLastName(),key);
			log.debug(locale.getLanguage());
		}
		if(String.valueOf(locale.getLanguage()).equals("hu")) {
			emailService.sendMessagehu(userToRegister.getEmail(),userToRegister.getFirstName(),userToRegister.getLastName(),key);
			log.debug(locale.getLanguage());
		}
		if(String.valueOf(locale.getLanguage()).equals("ro")) {
			emailService.sendMessagero(userToRegister.getEmail(),userToRegister.getFirstName(),userToRegister.getLastName(),key);
			log.debug(locale.getLanguage());
		}
		return "ok";
	}

	
	public String adminInit() {
		User user = new User();
		setAdminKey(generateKey());
		user.setEmail("TempEmail1"+"@"+ getAdminKey() +".uk");
		user.setFirstName("Temp FirstName");
		user.setLastName("Temp LastName");
		user.setPassword("Temp Password");
		user.setActivation(getAdminKey());
		userRepository.save(user);
		emailService.sendAdminCode(getAdminKey());
		log.debug(getAdminKey());
		return "Ok code";
	}
	
	@Override
	public String registerAdmin(User adminToregister) {
		adminToregister.setPassword(new BCryptPasswordEncoder().encode(adminToregister.getPassword()));
		adminToregister.setEnabled(true);
		Role userRole = roleRepository.findByRole(ADMIN_ROLE);
		if (userRole != null) {
			adminToregister.getRoles().add(userRole);
		} else {
			adminToregister.addRoles(ADMIN_ROLE);
		}
		userRepository.save(adminToregister);
		log.debug("regadmin aktivation " +adminToregister.getActivation());
		log.debug("regadmin email " +adminToregister.getEmail());
		log.debug("regadmin firstname " +adminToregister.getFirstName());
		log.debug("regadmin lastname " +adminToregister.getLastName());
		log.debug("regadmin password " +adminToregister.getPassword());
		log.debug("regadmin password " +adminToregister.getEnabled());
		log.debug("regadmin password " +adminToregister.getRoles());
		return"admin reg ok";
	}

	public String generateKey()
    {
		String key = ""; 
		Random random = new Random();
		char[] word = new char[16]; 
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		String toReturn = new String(word);
		log.debug("random code: " + toReturn);
		//System.out.println("random code: " + toReturn); Email  mar hasznalatban van
		return new String(word);
    }

	@Override
	public String userActivation(String code) {
		User user = userRepository.findByActivation(code);
		if (user == null)
		    return "noresult";
		
		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);
		return "ok";
	}

	@Override
	public User findByActivation(String activation) {
		User user = userRepository.findByActivation(activation);

		return user;
	}

	public String getAdminKey() {
		return adminKey;
	}

	public void setAdminKey(String adminKey) {
		this.adminKey = adminKey;
	}

	


}
