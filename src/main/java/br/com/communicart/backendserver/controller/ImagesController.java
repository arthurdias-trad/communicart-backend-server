package br.com.communicart.backendserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.communicart.backendserver.config.aws.AWSS3ServiceImpl;

@RestController
@RequestMapping("/api/images")
public class ImagesController {
	
	@Autowired
	private AWSS3ServiceImpl service;
	
	@PostMapping
	public ResponseEntity<String> uploadFile(@RequestPart(value= "file") final MultipartFile multipartFile) {
        final String name = service.uploadFile(multipartFile);
        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully as: " + name;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
}
