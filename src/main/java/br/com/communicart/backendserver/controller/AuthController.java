package br.com.communicart.backendserver.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.communicart.backendserver.model.entity.AuthResponse;
import br.com.communicart.backendserver.model.entity.Credential;
import br.com.communicart.backendserver.service.AuthService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/api/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody Credential credential) {
		AuthResponse authResponse = this.authService.authenticateUser(credential);
		return ResponseEntity.ok(authResponse);
	}
	
	@GetMapping("/api/validate")
	public ResponseEntity<Boolean> validateToken(@RequestHeader (name="Authorization") String authorizationHeader) {
		Boolean validated = this.authService.validateToken(authorizationHeader);
		return ResponseEntity.ok(validated);
	}
}
