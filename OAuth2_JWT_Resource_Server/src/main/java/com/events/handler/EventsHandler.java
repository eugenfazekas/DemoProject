package com.events.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.events.model.UserDetailsPrepare;
import com.service.UserService;

@EnableBinding(Sink.class)
public class EventsHandler {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	private UserService userService;
		
	public EventsHandler(UserService userService) {
		this.userService = userService;
	}

	@StreamListener(Sink.INPUT)
    public void listen(UserDetailsPrepare userloggedId) {
		
		log.debug("EventsHandler listen():  Authentication-Service Event, User started authenticating: " + userloggedId.getId());
		userService.prepareRedisUser(userloggedId.getId());
       
    }

}
