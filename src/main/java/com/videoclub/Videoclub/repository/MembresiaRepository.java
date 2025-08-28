package com.videoclub.Videoclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.Videoclub.entity.Membresia;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Integer>{

}
