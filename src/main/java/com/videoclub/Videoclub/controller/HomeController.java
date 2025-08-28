package com.videoclub.Videoclub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login-success")
    public String loginRedirect(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return "redirect:/login";
        }

        String rol = authentication.getAuthorities().iterator().next().getAuthority();

        switch (rol) {
            case "ROLE_Cliente":
                return "redirect:/catalogo";
            case "ROLE_Empleado":
                return "redirect:/alquileres"; 
            case "ROLE_Administrador":
                return "redirect:/alquileres";
            default:
                return "redirect:/login";
        }
    }
}



