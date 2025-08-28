package com.videoclub.Videoclub.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "detalle_alquiler")
public class DetalleAlquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddetalle;

    @ManyToOne
    @JoinColumn(name = "idalquiler")
    private Alquiler alquiler;

    @ManyToOne
    @JoinColumn(name = "idpelicula")
    private Pelicula pelicula;

    @Column(name = "tarifa")
    private BigDecimal tarifa;

	public DetalleAlquiler(Alquiler alquiler, Pelicula pelicula, BigDecimal tarifa) {
		this.alquiler = alquiler;
		this.pelicula = pelicula;
		this.tarifa = tarifa;
	}
    
    
}

