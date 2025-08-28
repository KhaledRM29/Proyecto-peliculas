package com.videoclub.Videoclub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.videoclub.Videoclub.entity.Pelicula;
import com.videoclub.Videoclub.repository.PeliculaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @GetMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable Integer id, HttpSession session) {
        List<Pelicula> carrito = (List<Pelicula>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        Pelicula pelicula = peliculaRepository.findById(id).orElseThrow();
        carrito.add(pelicula);
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito/ver";
    }

    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        List<Pelicula> carrito = (List<Pelicula>) session.getAttribute("carrito");
        model.addAttribute("carrito", carrito);
        return "carrito/ver";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDelCarrito(@PathVariable Integer id, HttpSession session) {
        List<Pelicula> carrito = (List<Pelicula>) session.getAttribute("carrito");
        if (carrito != null) {
            carrito.removeIf(p -> p.getIdpelicula().equals(id));
            session.setAttribute("carrito", carrito);
        }
        return "redirect:/carrito/ver";
    }
}

