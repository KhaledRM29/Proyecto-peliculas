package com.videoclub.Videoclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.Videoclub.entity.Alquiler;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer>{
	List<Alquiler> findByEstado_Nombrestado(String nombrestado);
}
