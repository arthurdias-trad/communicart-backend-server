package br.com.communicart.backendserver.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.communicart.backendserver.config.aws.AWSS3ServiceImpl;
import br.com.communicart.backendserver.model.enums.FileType;

@RestController
@RequestMapping("/api/awss3")
public class AWSS3Controller {

	@Autowired
	private AWSS3ServiceImpl service;

	@PostMapping("/images")
	public ResponseEntity<URL> uploadImage(@RequestPart(value = "file") final MultipartFile multipartFile,
			@RequestHeader(name = "Authorization") String authorizationHeader) {
		final URL url = service.uploadFile(multipartFile, authorizationHeader, FileType.IMAGE);
		return ResponseEntity.ok().body(url);
	}
	
	@PostMapping("/files")
	public ResponseEntity<URL> uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile,
			@RequestHeader(name = "Authorization") String authorizationHeader) {
		final URL url = service.uploadFile(multipartFile, authorizationHeader, FileType.DOCUMENT);
		return ResponseEntity.ok().body(url);
	}

}
