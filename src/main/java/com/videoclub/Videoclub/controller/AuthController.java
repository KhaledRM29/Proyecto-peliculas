package com.videoclub.Videoclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.Videoclub.entity.Estado;
import com.videoclub.Videoclub.entity.Rol;
import com.videoclub.Videoclub.entity.Usuario;
import com.videoclub.Videoclub.repository.UsuarioRepository;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value="logout", required=false) String logout,
                            @RequestParam(value="registered", required=false) String registered,
                            @RequestParam(value="error", required=false) String error,
                            Model model) {

        if(logout != null) {
            model.addAttribute("mensaje", "Sesión cerrada correctamente");
        }
        if(registered != null) {
            model.addAttribute("mensaje", "Usuario registrado correctamente. Por favor inicia sesión.");
        }
        if(error != null) {
            model.addAttribute("error", "El correo ya está registrado");
        }
        return "login"; 
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register"; 
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return "redirect:/register?error"; 
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        

        Rol rol = new Rol();
        rol.setIdrol(1); 
        Estado estado = new Estado();
        estado.setIdestado(1); 
        usuario.setRol(rol);
        usuario.setEstado(estado);

        usuarioRepository.save(usuario);
        return "redirect:/login?registered";
    }
}

