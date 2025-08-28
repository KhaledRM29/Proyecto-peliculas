package com.videoclub.Videoclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.videoclub.Videoclub.entity.Usuario;
import com.videoclub.Videoclub.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Usuario usuario = usuarioRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

	    return User.builder()
	            .username(usuario.getEmail())
	            .password(usuario.getContrasena())
	            .roles(usuario.getRol().getNombrerol())
	            .build();
	}

}
