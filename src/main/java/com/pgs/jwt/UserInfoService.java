package com.pgs.jwt;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.model.UsuarioModel;
import com.pgs.repository.IUsuarioRepository;
import com.pgs.service.EstadoServiceImpl;
import com.pgs.service.IEstadoService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoService implements UserDetailsService{
	
	private IUsuarioRepository repository;
	private IEstadoService estadoService;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <UsuarioModel> userDetail = this.repository
				.findByCorreoAndEstadosId(username, estadoService.buscarPorId(ControlGastosConstants.ESTADO_ACTIVO));
		return userDetail.map(UserInfoDetails :: new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
}
