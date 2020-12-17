package com.ajaj.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ajaj.document.Users;

public interface UserRepository extends MongoRepository<Users, String> {

}
