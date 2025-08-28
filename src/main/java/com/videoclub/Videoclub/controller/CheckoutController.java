package com.videoclub.Videoclub.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.Videoclub.entity.Usuario;
import com.videoclub.Videoclub.entity.Alquiler;
import com.videoclub.Videoclub.entity.DetalleAlquiler;
import com.videoclub.Videoclub.entity.Estado;
import com.videoclub.Videoclub.entity.Pelicula;
import com.videoclub.Videoclub.repository.AlquilerRepository;
import com.videoclub.Videoclub.repository.PeliculaRepository;
import com.videoclub.Videoclub.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/alquileres")
public class CheckoutController {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository; 


    @GetMapping("/checkout")
    public String mostrarCheckout(HttpSession session, Model model) {
        List<Pelicula> carrito = (List<Pelicula>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito/ver";
        }
        model.addAttribute("carrito", carrito);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String finalizarCompra(HttpSession session, Principal principal) {
        List<Pelicula> carrito = (List<Pelicula>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito/ver";
        }

        Alquiler alquiler = new Alquiler();
        alquiler.setFechaalquiler(new Date());
        alquiler.setEstado(new Estado(1));

        if (principal != null) {
            String email = principal.getName();
            Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
            alquiler.setUsuario(usuario);
        } else {
            throw new RuntimeException("Usuario no autenticado");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<DetalleAlquiler> detalles = new ArrayList<>();

        for (Pelicula pelicula : carrito) {
            int copiasFisicas = pelicula.getCopiafisicas() != null ? pelicula.getCopiafisicas() : 0;
            int copiasDigitales = pelicula.getCopiasdigitales() != null ? pelicula.getCopiasdigitales() : 0;

            if (copiasFisicas <= 0 && copiasDigitales <= 0) {
                continue;
            }

            DetalleAlquiler detalle = new DetalleAlquiler();
            detalle.setPelicula(pelicula);
            detalle.setTarifa(BigDecimal.valueOf(10.00));
            detalle.setAlquiler(alquiler);
            detalles.add(detalle);

            if (copiasFisicas > 0) {
                pelicula.setCopiafisicas(copiasFisicas - 1);
            } else {
                pelicula.setCopiasdigitales(copiasDigitales - 1);
            }
            peliculaRepository.save(pelicula);

            total = total.add(detalle.getTarifa());
        }

        alquiler.setDetalles(detalles);
        alquiler.setTarifa(total);
        alquilerRepository.save(alquiler);

        session.removeAttribute("carrito");

      
        return "redirect:/alquileres/confirmacion?total=" + total;
    }
    
    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(@RequestParam BigDecimal total, Model model) {
        model.addAttribute("total", total);
        return "confirmacion";
    }
    
    

}


