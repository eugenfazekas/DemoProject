 package com.demoproject.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demoproject.entity.Blogs;
import com.demoproject.entity.Index;
import com.demoproject.entity.User;
import com.demoproject.service.BlogServiceImpl;
import com.demoproject.service.IndexService;
import com.demoproject.service.UserService;

//@Secured("USER")

@ControllerAdvice
@Controller
public class HomeController {
	
	private String authName;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private UserService userService;
	
	private BlogServiceImpl blogServiceImpl;
	
	private IndexService indexService;
	
	private String index;
	
		
	@Autowired
	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}


	@Autowired
	public void setBlogServiceImpl(BlogServiceImpl blogServiceImpl) {
		this.blogServiceImpl = blogServiceImpl;
	}

	
	//	@Qualifier("UserServiceImpl")
	@Autowired	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		
	   	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();{

	    	if (principal instanceof UserDetails) {
	    	  String username = ((UserDetails)principal).getUsername();
	    	  
	    	  User user = userService.findByEmail(username);
	    	  
	    	  if(user.getLastName() != null) {
	    		  
		    		setAuthName(user.getLastName()+" "+user.getFirstName()) ;
		    	  }else {
		    		  setAuthName("");
		    	  }
	   
	    	  model.addAttribute("fullname", authName);
	    	} 	  
	    }
	}
	
	@RequestMapping("/")
	
	public String home(Model model) {
		if(getIndex()==null) {
			model.addAttribute("story", new Index());
			return "index";
			}else {
				Index index1 = indexService.searchtitle(getIndex());
				model.addAttribute("story",index1 )	;
				}
				return "index";
			}
	
	@RequestMapping("/main")
	public String main() {
		return "layouts/main";
	}
	
	@RequestMapping("/login")
	public String login() {
	
		return"forward:/index";
	}
	
	@RequestMapping("/index")
	public String index(Model model) {
		if(getIndex()==null) {
		model.addAttribute("story", new Index());
		return "index";
		}else {
		Index index1 = indexService.searchtitle(getIndex());
		model.addAttribute("story",index1 )	;
		log.debug(index1.getVideo1());
		log.debug(index1.getImage1());
		}
		return "index";
	}
	
	@RequestMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user",new User());
		return "registration";
	}
	
	//@RequestMapping(value="/reg",method = RequestMethod.POST)
	@PostMapping("/reg")
	public String reg(@ModelAttribute User user,@Valid User user1, Errors errors,Model model,Locale locale) {
		if( errors.hasErrors()) {
			return "registration";
		}
		if(userService.findByEmail(user.getEmail()) != null ){
			model.addAttribute("exist", " ");
			return "registration";
		}
		else {
			model.addAttribute("message"," ");
		}
		log.info("Uj user!");
		log.debug(user.getFirstName());
		log.debug(user.getLastName());
		log.debug(user.getEmail());
		log.debug(user.getPassword());
		userService.registerUser(user,locale);
			return "forward:/index";
	}
	
	 @RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
	    public String activation(@PathVariable("code") String code, HttpServletResponse response,Model model) {
	    String result = userService.userActivation(code);
	    if(result.equals("ok")) {
	    	model.addAttribute("activation"," ");
	    }
		return "forward:/index";
	 }
	
	 @RequestMapping("/blogs")
		public String blogregistration(Model model) {
			model.addAttribute("blogs",new Blogs());
			model.addAttribute("blogsout", blogServiceImpl.getBlogs());
			return "blogs";
		}
	 
	 @PostMapping("/blogreg")
		public String blogreg(@ModelAttribute Blogs blogs, Errors errors,Model model) {
		 
		 setAuthName("Anonymus");
		 
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();{
		    	if (principal instanceof UserDetails) {
		    	  String username = ((UserDetails)principal).getUsername();
		    	  User user = userService.findByEmail(username);
		    	  if(user.getLastName() != null) {
		    	  setAuthName(user.getLastName().toString()+" "+user.getFirstName().toString()) ;
			    	  }
		    } 
		    	log.info("Uj Hozzaszolas");
				log.debug(blogs.getTitle());
				log.debug(blogs.getContent());
				blogs.setBlogger(authName);
				log.debug(authName);
				blogServiceImpl.registerBlogs(blogs);   	
				log.debug("ez a Homebol "+ blogs.toString());
		 }
		 return "forward:/blogs";
 }
	
	@RequestMapping("/blog")
	public String videos1 (Model model ,@RequestParam(defaultValue="")String search ) {
		log.debug("Search: " +search);
		model.addAttribute("blogsearch", blogServiceImpl.findBySearch(search));
			return "blog";
	}
	
//	@RequestMapping("/blog")
//	public String blog(Model model) {
//		model.addAttribute("blogsearch", blogServiceImpl.findBySearch("aa"));
//		return "blog";
//	}
	 
	@RequestMapping("/videos")
	public String videos() {
		return "videos";
	}
	
	@RequestMapping("/description")
	public String description() {
		return "description";
	}
	
	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	
	@RequestMapping("/getadminkey")
	public String admininit() {
		userService.adminInit();
		return "detailederror";
		}
	
	@RequestMapping("/adminregistration")
		public String adminregreturn() {
		
		return "/index";
	}
	
	 @RequestMapping(path = "/adminrequestcode/{code}", method = RequestMethod.GET)
	    public String adminRegisration(@PathVariable("code") String code, HttpServletResponse response, Model model) {
	    User user =  userService.findByActivation(code);
	    if(user.getActivation().equals(null))	{
		return "index";
	    }else {
	    	user.setEmail(null);
	    	user.setFirstName(null);
	    	user.setLastName(null);
	    	user.setPassword(null);
	    	model.addAttribute("user", user);
	    	return "adminregistration";
	    }
	 }
	 
		@PostMapping("/adminreg")
		public String adminreg(@ModelAttribute User user, Model model) {
			if(getIndex()==null) {
				model.addAttribute("story", new Index());
				log.info("Uj Admin!");
				log.debug("adminreg firstname"+user.getFirstName());
				log.debug("adminreg lastname"+user.getLastName());
				log.debug("adminreg email"+user.getEmail());
				log.debug("adminreg password"+user.getPassword());
				log.debug("adminreg role"+user.getRoles());
				userService.registerAdmin(user);
				return "index";
				}else {
				Index index1 = indexService.searchtitle(getIndex());
				model.addAttribute("story",index1 )	;
								}
			log.info("Uj Admin!");
			log.debug("adminreg firstname"+user.getFirstName());
			log.debug("adminreg lastname"+user.getLastName());
			log.debug("adminreg email"+user.getEmail());
			log.debug("adminreg password"+user.getPassword());
			log.debug("adminreg role"+user.getRoles());
			userService.registerAdmin(user);
				return "index";
		}
		
	@RequestMapping("admin/addstory")
	public String addstory(Model model) {
		model.addAttribute("index", new Index());
		return "admin/addstory";
	}
		
	 @PostMapping("/indexreg")
	 public String indexreg(@ModelAttribute Index index,Model model){
		 if(indexService.findByTitle(index.getTitle()) != null ){
				model.addAttribute("exist", " ");
				return "admin/addstory";
			}else {
				log.debug(index.getTitle());
				log.debug(index.getContent1());
				log.debug(index.getContent2());
				log.debug(index.getContent3());
				log.debug(index.getVideo1());
				log.debug(index.getImage1());
		 indexService.registerIndex(index);
		 return "forward:/admin/stories";
	 }
 }
	 @RequestMapping("admin/stories")
	 public String stories(Model model) {
		
		 model.addAttribute("stories", indexService.getIndex());
		 return "admin/stories";
				}
	 
	 
	 @RequestMapping("admin/setstory")
		public String story (Model model ,@RequestParam(defaultValue="")String storysearch ) {
		 log.debug("Search: " +storysearch);
			model.addAttribute("storysearch", indexService.searchtitle(storysearch));
			setIndex(storysearch);
				return "forward:/admin/stories";
		}
	 
	 @RequestMapping("admin/storydelete")
		public String storydelete (Model model ,@RequestParam(defaultValue="")String storydelete ) {
		 log.debug("Deleted Story: " +storydelete);
			indexService.deleteindex(storydelete);
		
					return "forward:/admin/stories";
		}
	 
	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}


	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}

	
}
