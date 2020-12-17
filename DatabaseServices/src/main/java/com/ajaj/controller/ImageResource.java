package com.ajaj.controller;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ajaj.document.ImageData;
import com.ajaj.document.Users;
import com.ajaj.repository.ImageRpository;
import com.ajaj.repository.UserRepository;

@RestController
@RequestMapping("/imageData")
public class ImageResource {

	@Autowired
	private ImageRpository imageRpository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "getprofilepic/{userId}")
	public ResponseEntity<Resource> getImages(@PathVariable String userId) {
		Optional<ImageData> ImageData = imageRpository.findById(userId);
		if (ImageData.isPresent()) {
			if (ImageData.get().getProfile_pic() != null) {
				 InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(ImageData.get().getProfile_pic()));
				 HttpHeaders headers = new HttpHeaders();
				 return ResponseEntity.ok()
			                .contentType(MediaType.IMAGE_JPEG)
			                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=profile_pic.jpeg"))
			                .body(new ByteArrayResource(ImageData.get().getProfile_pic()));
				 
				
			}

		}
		return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);

	}

	@PostMapping(path = "/saveprofilepic/{userId}")
	public ResponseEntity<String> saveImages(@ModelAttribute MultipartFile file, @PathVariable String userId)
			throws IOException {

		if (!file.getContentType().equals("image/jpeg")) {
			return new ResponseEntity("Only JPEG format supported upto 10 MB", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}

		// CHecking user Id in user profile
		Optional<Users> Users = userRepository.findById(userId);
		if (Users.isPresent()) {
			ImageData img = new ImageData();
			img.setUserId(userId);
			img.setProfile_pic(file.getBytes());
			imageRpository.save(img);
			return new ResponseEntity<>("Image Saved Succesfully", HttpStatus.OK);
		}

		return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);
	}

}
