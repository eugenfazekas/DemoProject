package com.events.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.events.model.UserDetailsPrepare;

@Component
public class SimpleSourceBean {
	
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    @Autowired
    public SimpleSourceBean(Source source){
        this.source = source;
    }

    public boolean publisUserAuthenticationId(String id){
    	
       boolean sent = false;
    	
       if(id ==  "" ||  id ==  null ) {
			throw new RuntimeException(
			"Authentication_Server.SimpleSourceBean --> id cannot be null or empty!");
			}
       
       logger.debug("Sending Kafka message {} for Resource-Server Id: {}", id);
       UserDetailsPrepare change =  new UserDetailsPrepare(id);
       
       sent = source.output().send(MessageBuilder.withPayload(change).build());
        
      return sent;
    }
}
