package com.videoclub.Videoclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.Videoclub.entity.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer>{

}
