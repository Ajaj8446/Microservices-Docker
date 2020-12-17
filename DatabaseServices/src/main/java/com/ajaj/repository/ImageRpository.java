package com.ajaj.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ajaj.document.ImageData;

public interface ImageRpository extends MongoRepository<ImageData, String> {

}
