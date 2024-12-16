package com.pgs.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.model.UsuarioModel;
import com.pgs.service.IVariableGlobalService;
import com.pgs.service.VariableGlobalServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtService {

	private IVariableGlobalService variableGlobalService;
	
	public String generateToken (String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date (System.currentTimeMillis() + (24*7)*60*60*1000))
				.signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
	}

	private Key getSignKey() {
		byte [] keyBytes = Decoders.BASE64
				.decode(this.variableGlobalService.buscarPorId(ControlGastosConstants.SECRET_JWT).getValor());
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
		
	}
	
	public Date extractExpiration(String token) {
	    return extractClaim(token, Claims::getExpiration);
	}
	

	public <T> T extractClaim(String token, Function <Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims); // Saca solo el claim que interesa
	}
	
	public Boolean validateToken(String token, UsuarioModel userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getCorreo()) && !isTokenExpired(token));
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
