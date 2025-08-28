package com.videoclub.Videoclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.Videoclub.entity.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer>{
	List<Pelicula> findByEstadoNombrestado(String nombrestado);
	List<Pelicula> findByTituloContainingIgnoreCaseAndEstadoNombrestado(String titulo, String nombrestado);}
