package br.com.communicart.backendserver.controller;

import javax.validation.Valid;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.communicart.backendserver.model.entity.AuthResponse;
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
}
