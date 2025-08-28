package com.videoclub.Videoclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.Videoclub.entity.Alquiler;
import com.videoclub.Videoclub.entity.DetalleAlquiler;
import com.videoclub.Videoclub.entity.Estado;
import com.videoclub.Videoclub.entity.Pelicula;
import com.videoclub.Videoclub.repository.AlquilerRepository;
import com.videoclub.Videoclub.repository.EstadoRepository;
import com.videoclub.Videoclub.repository.PeliculaRepository;
import com.videoclub.Videoclub.repository.UsuarioRepository;

@Controller
@RequestMapping("/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping
    public String listarAlquileres(@RequestParam(value = "mostrar", defaultValue = "activos") String mostrar, Model model) {
        List<Alquiler> alquileres;

        if ("inactivos".equals(mostrar)) {
            alquileres = alquilerRepository.findByEstado_Nombrestado("Inactivo");
        } else if ("todos".equals(mostrar)) {
            alquileres = alquilerRepository.findAll();
        } else {
            alquileres = alquilerRepository.findByEstado_Nombrestado("Activo");
        }
        model.addAttribute("alquileres", alquileres);
        model.addAttribute("mostrar", mostrar);
        return "alquileres/lista";
    }


    @GetMapping("/nuevo")
    public String nuevoAlquiler(Model model) {
        Alquiler alquiler = new Alquiler();
        DetalleAlquiler detalle = new DetalleAlquiler();
        detalle.setAlquiler(alquiler);
        alquiler.getDetalles().add(detalle);

        model.addAttribute("alquiler", alquiler);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("peliculas", peliculaRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        return "alquileres/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarAlquiler(@PathVariable Integer id, Model model) {
        Alquiler alquiler = alquilerRepository.findById(id).orElseThrow();
        if (alquiler.getDetalles().isEmpty()) {
            DetalleAlquiler detalle = new DetalleAlquiler();
            detalle.setAlquiler(alquiler);
            alquiler.getDetalles().add(detalle);
        }

        model.addAttribute("alquiler", alquiler);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("peliculas", peliculaRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        return "alquileres/formulario";
    }

    @PostMapping("/guardar")
    public String guardarAlquiler(@ModelAttribute Alquiler alquiler) {
        if (alquiler.getIdalquiler() == null) { 
            for (DetalleAlquiler detalle : alquiler.getDetalles()) {
                Pelicula pelicula = detalle.getPelicula();
                if (pelicula != null) {
                    int copiasFisicas = pelicula.getCopiafisicas() != null ? pelicula.getCopiafisicas() : 0;
                    int copiasDigitales = pelicula.getCopiasdigitales() != null ? pelicula.getCopiasdigitales() : 0;

                    if (copiasFisicas > 0) {
                        pelicula.setCopiafisicas(copiasFisicas - 1);
                    } else if (copiasDigitales > 0) {
                        pelicula.setCopiasdigitales(copiasDigitales - 1);
                    } else {
                        throw new RuntimeException("No hay copias disponibles de la película: " + pelicula.getTitulo());
                    }

                    peliculaRepository.save(pelicula);
                }
            }
        }
        
        for (DetalleAlquiler detalle : alquiler.getDetalles()) {
            detalle.setAlquiler(alquiler);
        }
        
        alquilerRepository.save(alquiler);
        return "redirect:/alquileres";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Integer id) {
        Alquiler alquiler = alquilerRepository.findById(id).orElseThrow();
        Estado inactivo = estadoRepository.findByNombrestado("Inactivo");
        alquiler.setEstado(inactivo);
        alquilerRepository.save(alquiler);
        return "redirect:/alquileres";
    }   
}

