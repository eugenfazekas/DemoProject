package com.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Repository;

import com.model.Category;
import com.model.User;
import com.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	private final String USERS_COLLECTION = "users";

	@Override
	public void createCollectionUsers() {	

	 MongoJsonSchema schema = MongoJsonSchema.builder()                                                  
			    .required("date_registered")                                     
			    .properties(
			    		JsonSchemaProperty.string("id").minLength(3).maxLength(100),         
			    		JsonSchemaProperty.string("firstName").minLength(3).maxLength(15),
			    		JsonSchemaProperty.string("lastName").minLength(3).maxLength(15), 
			    		JsonSchemaProperty.string("fullName").minLength(3).maxLength(30),
			    		JsonSchemaProperty.string("date_registered"),
			    		JsonSchemaProperty.string("activeProfilePhoto").maxLength(70), 
			    		JsonSchemaProperty.object("Address").properties(
			    														JsonSchemaProperty.string("country").possibleValues("Romania","Hungary","UK"),
			    														JsonSchemaProperty.string("city").minLength(3).maxLength(20), 
			    														JsonSchemaProperty.string("street").minLength(3).maxLength(25), 
			    														JsonSchemaProperty.string("number").minLength(1).maxLength(5) 
			    									),
			    		JsonSchemaProperty.array("profilePhotos").items(JsonSchemaProperty.string("items")),
			    		JsonSchemaProperty.array("articles").items(JsonSchemaProperty.string("items")))
			    	    .build();
		
		 if(!mongoTemplate.collectionExists(USERS_COLLECTION))
		mongoTemplate.createCollection(USERS_COLLECTION, CollectionOptions.empty().schema(schema));
		log.debug("Users Collection created");
		}

	@Override
	public void dropCollectionUsers() {
		
		if(mongoTemplate.collectionExists(USERS_COLLECTION))
		mongoTemplate.dropCollection(USERS_COLLECTION);
		 log.debug("Users Collection deleted");
	}

	@Override
	public void createUser(User user) {
		
		mongoTemplate.insert(user, USERS_COLLECTION);	
		log.debug(user.toString());
	}

	@Override
	public void addArticle(String userFullName, String article) {

		Query query = new Query();
		query.addCriteria(Criteria.where("fullName").is(userFullName));
		mongoTemplate.updateFirst(query, new Update().push("articles", article), Category.class, USERS_COLLECTION);
	}

	@Override
	public void deleteArticle(String userFullName, String article) {

		Query query = new Query();
		query.addCriteria(Criteria.where("fullName").is(userFullName));
		mongoTemplate.updateFirst(query, new Update().pull("articles", article), Category.class, USERS_COLLECTION);
		
	}

	@Override
		public User findUserById(String id) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		User user = mongoTemplate.findOne(query, User.class, USERS_COLLECTION);
		return user;
		
	}

	@Override
	public User updateUser(User user) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(user.getId()));
			Update update = new Update();
			update.set("firstName", user.getFirstName());
			update.set("lastName", user.getLastName());
			update.set("fullName", user.getFullName());
			update.set("address", user.getAddress());
			mongoTemplate.updateMulti(query, update, User.class, USERS_COLLECTION);
			log.debug("Updated user " + user.toString());
		return user;
	}

	@Override
	public void uploadProfilePhoto(String userId, String photoName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userId));
		mongoTemplate.updateFirst(query, new Update().push("profilePhotos", photoName), User.class, USERS_COLLECTION);	
	}

	@Override
	public void deleteProfilePhoto(String userId, String photoName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userId));
		mongoTemplate.updateFirst(query, new Update().pull("profilePhotos", photoName), User.class, USERS_COLLECTION);	
	}

	@Override
	public void setActiveProfilePhoto(String userId, String photoName) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userId));
		Update update = new Update();
		update.set("activeProfilePhoto", photoName);
		mongoTemplate.updateFirst(query, update, User.class, USERS_COLLECTION);	
	}
}
