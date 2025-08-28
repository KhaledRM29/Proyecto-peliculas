package com.videoclub.Videoclub.entity;

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
@Table(name = "estado")
public class Estado {
	@Id
	@Column(name = "idestado")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idestado;
	
	@Column(name = "nombrestado")
	private String nombrestado;

	

	public Estado(Integer idestado) {
		this.idestado = idestado;
	}



	public Estado(String nombrestado) {
		this.nombrestado = nombrestado;
	}
	
	
}
