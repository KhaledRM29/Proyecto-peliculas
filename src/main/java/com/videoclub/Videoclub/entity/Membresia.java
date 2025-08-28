package com.videoclub.Videoclub.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "membresia")
public class Membresia {
	@Id
	@Column(name = "idmembresia")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idmembresia;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "beneficios")
	private String beneficios;
	
	@Column(name = "precio")
	private BigDecimal precio;
	
	@Column(name = "duracion")
	private String duracion;
}
