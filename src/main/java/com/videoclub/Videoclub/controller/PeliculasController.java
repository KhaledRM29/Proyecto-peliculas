package com.videoclub.Videoclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.Videoclub.entity.Estado;
import com.videoclub.Videoclub.entity.Pelicula;
import com.videoclub.Videoclub.repository.CategoriaRepository;
import com.videoclub.Videoclub.repository.EstadoRepository;
import com.videoclub.Videoclub.repository.GeneroRepository;
import com.videoclub.Videoclub.repository.PeliculaRepository;

@Controller
@RequestMapping("/peliculas")
public class PeliculasController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping
    @PreAuthorize("hasRole('Administrador')")
    public String listarPeliculas(
            @RequestParam(value = "estado", defaultValue = "Activo") String estado,
            @RequestParam(required = false) String filtro,
            Model model) {

        List<Pelicula> peliculas;

        if (filtro != null && !filtro.isEmpty()) {
            peliculas = peliculaRepository.findByTituloContainingIgnoreCaseAndEstadoNombrestado(filtro, estado);
        } else {
            peliculas = peliculaRepository.findByEstadoNombrestado(estado);
        }

        model.addAttribute("peliculas", peliculas);
        model.addAttribute("filtro", filtro);
        model.addAttribute("estado", estado);

        return "peliculas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoPelicula(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("generos", generoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        return "peliculas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarPelicula(@PathVariable Integer id, Model model) {
        Pelicula pelicula = peliculaRepository.findById(id).orElseThrow();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", generoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        return "peliculas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPelicula(@ModelAttribute Pelicula pelicula) {
        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLogico(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepository.findById(id).orElseThrow();
        Estado inactivo = estadoRepository.findByNombrestado("Inactivo"); 
        pelicula.setEstado(inactivo);
        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }
    @GetMapping("/activar/{id}")
    public String activarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepository.findById(id).orElseThrow();
        Estado activo = estadoRepository.findByNombrestado("Activo");
        pelicula.setEstado(activo);
        peliculaRepository.save(pelicula);
        return "redirect:/peliculas?estado=Inactivo";
    }
    
}

