package com.events.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.events.model.UserDetailsPrepare;
import com.service.UserService;

import brave.ScopedSpan;
import brave.Tracer;

@EnableBinding(Sink.class)
public class EventsHandler {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	private UserService userService;
		
	public EventsHandler(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	Tracer tracer;
	
	@StreamListener(Sink.INPUT)
    public void listenToAuthentication(UserDetailsPrepare userloggedId) {
		
		ScopedSpan newSpan1 = tracer.startScopedSpan("listenToAuthentication");
		newSpan1.tag("Resource-service EventsHandler listenToAuthentication with redis cache", "User listenToAuthentication");
		newSpan1.annotate("User listenToAuthentication");
		newSpan1.finish();
		log.debug("EventsHandler listen():  Authentication-Service Event, User started authenticating: " + userloggedId.getId());
		userService.prepareRedisUser(userloggedId.getId());
       
    }

}
