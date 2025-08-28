package com.videoclub.Videoclub.entity;

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
@Table(name = "usuario")
public class Usuario {
	@Id
	@Column(name = "idusuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idusuario;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellido")
	private String apellido;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "contrasena")
	private String contrasena;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "direccion")
	private String direccion;
	
	@ManyToOne
	@JoinColumn(name = "idrol")
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name = "idestado")
	private Estado estado;
	
	@ManyToOne
	@JoinColumn(name = "idmembresia")
	private Membresia membresia;


	public Usuario(String nombre, String apellido, String email, String contrasena, String telefono, String direccion,
			Rol rol, Estado estado, Membresia membresia) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contrasena = contrasena;
		this.telefono = telefono;
		this.direccion = direccion;
		this.rol = rol;
		this.estado = estado;
		this.membresia = membresia;
	}
	
	
}
