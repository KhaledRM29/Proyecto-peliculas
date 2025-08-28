package com.videoclub.Videoclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videoclub.Videoclub.entity.Estado;
import com.videoclub.Videoclub.entity.Usuario;
import com.videoclub.Videoclub.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarActivos() {
        return usuarioRepository.findByEstadoIdestado(1);
    }

    public List<Usuario> listarInactivos() {
        return usuarioRepository.findByEstadoIdestado(2);
    }
    
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void eliminarLogico(Integer id) {
    	Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario !=null ) {
        	Estado estadoInactivo = new Estado();
        	estadoInactivo.setIdestado(2);
        	usuario.setEstado(estadoInactivo);
        	usuarioRepository.save(usuario);
        }
    	
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
