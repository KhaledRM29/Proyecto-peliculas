package com.videoclub.Videoclub.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "alquiler")
public class Alquiler {
	@Id
	@Column(name = "idalquiler")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idalquiler;
	
	@ManyToOne
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@Column(name = "fechaalquiler")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaalquiler;
	
	@Column(name = "fechadevolucion")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechadevolucion;
	
	@ManyToOne
	@JoinColumn(name = "idestado")
	private Estado estado;
	
	@Column(name = "tarifa")
	private BigDecimal tarifa;
	
	@Column(name = "mora")
	private BigDecimal mora;
	
	@OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL)
    private List<DetalleAlquiler> detalles = new ArrayList<>();

	public Alquiler(Usuario usuario, Date fechaalquiler, Date fechadevolucion, Estado estado, BigDecimal tarifa,
			BigDecimal mora, List<DetalleAlquiler> detalles) {
		this.usuario = usuario;
		this.fechaalquiler = fechaalquiler;
		this.fechadevolucion = fechadevolucion;
		this.estado = estado;
		this.tarifa = tarifa;
		this.mora = mora;
		this.detalles = detalles;
	}
}
