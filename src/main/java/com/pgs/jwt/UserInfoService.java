package com.pgs.jwt;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.model.UsuarioModel;
import com.pgs.repository.IUsuarioRepository;
import com.pgs.service.IEstadoService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Service class that implements {@link UserDetailsService} to load user-specific data.
 * This service is used for retrieving user details based on the username, integrating with Spring Security.
 * <p>It fetches active user information from the repository and returns a {@link UserInfoDetails} object.</p>
 */
@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoService implements UserDetailsService{
	
	private IUsuarioRepository repository;
	private IEstadoService estadoService;
	

	/**
	 * Loads user details by username.
	 * This method is called by Spring Security to load user information during authentication.
	 * 
	 * @param username The username (usually email) of the user.
	 * @return A {@link UserInfoDetails} object containing user details, or throws an exception if not found.
	 * @throws UsernameNotFoundException If the user with the given username is not found or is inactive.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <UsuarioModel> userDetail = this.repository
				.findByCorreoAndEstadosId(username, estadoService.buscarPorId(ControlGastosConstants.ESTADO_ACTIVO));
		return userDetail.map(UserInfoDetails :: new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
}
