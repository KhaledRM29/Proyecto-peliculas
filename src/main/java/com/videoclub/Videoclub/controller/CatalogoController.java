package com.videoclub.Videoclub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.videoclub.Videoclub.entity.Pelicula;
import com.videoclub.Videoclub.repository.PeliculaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {
	@Autowired
    private PeliculaRepository peliculaRepository;

    @GetMapping
    public String verCatalogo(Model model) {
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "catalogo/index"; 
    }

    @PostMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable Integer id, HttpSession session) {
        Pelicula pelicula = peliculaRepository.findById(id).orElse(null);
        if (pelicula != null) {
            List<Pelicula> carrito = (List<Pelicula>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }
            carrito.add(pelicula);
            session.setAttribute("carrito", carrito);
        }
        return "redirect:/catalogo";
    }
}
