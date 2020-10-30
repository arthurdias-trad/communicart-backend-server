package br.com.communicart.backendserver.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.communicart.backendserver.model.entity.Perfil;
import br.com.communicart.backendserver.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value("${jwt.expiration}")
	private Long exp;

	@Value("${jwt.secretKey}")
	private String secret;
	
	public String generateToken(Usuario usuario) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("usrid", usuario.getId());
		claims.put("profid", usuario.getPerfil().getId());
		
		
		return Jwts.builder()
				.setExpiration(new Date(System.currentTimeMillis() + exp))
				.addClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	public Usuario getUser(String jwt) {
		Claims claims = this.getClaims(jwt);
		
		Long id = claims.get("usrid", Long.class);
		Long perfilId = claims.get("profid", Long.class);
			
		
		return Usuario.builder()
				.id(id)
				.perfil(Perfil.builder().id(perfilId).build())
				.build();
	}
	
	public Long getProfileId(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.get("profid", Long.class);
		}
		
		return null;
	}
	
	public boolean validateToken(String token) {
		Claims claims = getClaims(token);
		Date agora = new Date(System.currentTimeMillis());
		
		System.out.println(claims);
		System.out.println(claims != null && agora.before(claims.getExpiration()));
		
		return claims != null && agora.before(claims.getExpiration());
	}
	
	public Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch(Exception e) {
			return null;
		}
	}
	
}
