package com.videoclub.Videoclub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.Videoclub.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Optional<Usuario> findByEmail(String email);
	List<Usuario> findByNombreContainingIgnoreCase(String nombre);
	List<Usuario> findByEstadoIdestado(Integer idestado);
    List<Usuario> findByApellidoContainingIgnoreCase(String apellido);
    List<Usuario> findByRolNombrerol(String nombrerol);
}
