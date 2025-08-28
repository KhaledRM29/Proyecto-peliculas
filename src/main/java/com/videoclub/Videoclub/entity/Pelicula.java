package com.videoclub.Videoclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pelicula")
public class Pelicula {
	@Id
	@Column(name = "idpelicula")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idpelicula;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "director")
	private String director;
	
	@Column(name = "anio")
	private Integer anio;
	
	@Lob
	@Column(name = "sinopsis")
	private String sinopsis;
	
	@Column(name = "copiafisicas")
	private Integer copiafisicas;
	
	@Column(name = "imagen")
	private String imagen;

	@Column(name = "copiasdigitales")
	private Integer copiasdigitales;
	
	@ManyToOne
	@JoinColumn(name = "idgenero")
	private Genero genero;
	
	@ManyToOne
	@JoinColumn(name = "idcategoria")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "idestado")
	private Estado estado;

	public Pelicula(String titulo, String director, Integer anio, String sinopsis, int copiafisicas, String imagen,
			int copiasdigitales, Genero genero, Categoria categoria, Estado estado) {
		this.titulo = titulo;
		this.director = director;
		this.anio = anio;
		this.sinopsis = sinopsis;
		this.copiafisicas = copiafisicas;
		this.imagen = imagen;
		this.copiasdigitales = copiasdigitales;
		this.genero = genero;
		this.categoria = categoria;
		this.estado = estado;
	}

	
	
	
}
