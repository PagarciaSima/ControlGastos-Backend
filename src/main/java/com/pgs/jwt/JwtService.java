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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

/**
 * Service class responsible for handling JWT token generation, validation, and claim extraction.
 * Implements the {@link IJwtService} interface.
 * <p>This service generates JWT tokens using a secret key, extracts information from tokens, and 
 * validates the token against a user model.</p>
 */
@Service
@AllArgsConstructor
public class JwtService implements IJwtService {

	private IVariableGlobalService variableGlobalService;
	
	/**
	 * Generates a JWT token for the given username.
	 * 
	 * @param username The username (typically email) for which the token will be generated.
	 * @return A string representing the generated JWT token.
	 */
	@Override
	public String generateToken (String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	/**
	 * Creates a JWT token with the provided claims and username.
	 * 
	 * @param claims   A map of claims to include in the token.
	 * @param username The username (subject) for the token.
	 * @return A string representing the generated JWT token.
	 */
	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date (System.currentTimeMillis() + (24*7)*60*60*1000))
				.signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
	}

	/**
	 * Retrieves the secret signing key for JWT.
	 * 
	 * @return The key used for signing JWT tokens.
	 */
	private Key getSignKey() {
		byte [] keyBytes = Decoders.BASE64
				.decode(this.variableGlobalService.buscarPorId(ControlGastosConstants.SECRET_JWT).getValor());
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	/**
	 * Extracts the username (subject) from the JWT token.
	 * 
	 * @param token The JWT token from which to extract the username.
	 * @return The username (subject) contained in the token.
	 */
	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
		
	}
	
	/**
	 * Extracts the expiration date from the JWT token.
	 * 
	 * @param token The JWT token from which to extract the expiration date.
	 * @return The expiration date of the token.
	 */
	@Override
	public Date extractExpiration(String token) {
	    return extractClaim(token, Claims::getExpiration);
	}
	

	/**
	 * Extracts a specific claim from the JWT token.
	 * 
	 * @param token           The JWT token from which to extract the claim.
	 * @param claimsResolver A function to extract the specific claim from the token.
	 * @param <T>            The type of the claim to be extracted.
	 * @return The extracted claim of type {@code T}.
	 */
	@Override
	public <T> T extractClaim(String token, Function <Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims); // Saca solo el claim que interesa
	}
	
	/**
	 * Validates the JWT token by checking if it is expired and if the username matches the user details.
	 * 
	 * @param token        The JWT token to validate.
	 * @param userDetails  The user details (usually fetched from a database or authentication system).
	 * @return {@code true} if the token is valid, {@code false} otherwise.
	 */
	@Override
	public Boolean validateToken(String token, UsuarioModel userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getCorreo()) && !isTokenExpired(token));
	}
	
	/**
	 * Extracts all claims from the JWT token.
	 * 
	 * @param token The JWT token from which to extract the claims.
	 * @return The claims of the token.
	 */
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	/**
	 * Checks if the JWT token has expired.
	 * 
	 * @param token The JWT token to check.
	 * @return {@code true} if the token is expired, {@code false} otherwise.
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
