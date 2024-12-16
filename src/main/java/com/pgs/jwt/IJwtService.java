package com.pgs.jwt;

import java.util.Date;
import java.util.function.Function;

import com.pgs.model.UsuarioModel;

import io.jsonwebtoken.Claims;

public interface IJwtService {

	String generateToken(String username);

	String extractUsername(String token);

	Date extractExpiration(String token);

	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);

	Boolean validateToken(String token, UsuarioModel userDetails);

}