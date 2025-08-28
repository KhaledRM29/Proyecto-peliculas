package com.videoclub.Videoclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.Videoclub.entity.Estado;
import com.videoclub.Videoclub.entity.Membresia;
import com.videoclub.Videoclub.entity.Rol;
import com.videoclub.Videoclub.entity.Usuario;
import com.videoclub.Videoclub.repository.EstadoRepository;
import com.videoclub.Videoclub.repository.MembresiaRepository;
import com.videoclub.Videoclub.repository.RolRepository;
import com.videoclub.Videoclub.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String listarUsuarios(@RequestParam(value = "estado", defaultValue = "activos") String estado, Model model) {
        if ("inactivos".equals(estado)) {
            model.addAttribute("usuarios", usuarioService.listarInactivos());
        } else {
            model.addAttribute("usuarios", usuarioService.listarActivos());
        }
        model.addAttribute("estado", estado);
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        Usuario usuario = new Usuario();
        usuario.setRol(new Rol());
        usuario.setEstado(new Estado());
        usuario.setMembresia(new Membresia());

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("membresias", membresiaRepository.findAll());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {

        if (usuario.getIdusuario() == null) {
            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                throw new IllegalArgumentException("La contraseña es obligatoria para un nuevo usuario.");
            }
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        } else {
            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                Usuario existente = usuarioService.buscarPorId(usuario.getIdusuario());
                usuario.setContrasena(existente.getContrasena());
            } else {
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }
        }

        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario.getRol() == null) usuario.setRol(new Rol());
        if (usuario.getEstado() == null) usuario.setEstado(new Estado());
        if (usuario.getMembresia() == null) usuario.setMembresia(new Membresia());

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("estados", estadoRepository.findAll());
        model.addAttribute("membresias", membresiaRepository.findAll());
        return "usuarios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarLogico(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/buscar")
    public String buscarUsuario(@RequestParam("nombre") String nombre, Model model) {
        List<Usuario> usuarios = usuarioService.buscarPorNombre(nombre);
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista";
    }
}
