package com.hibernate.ferreteria.controladores;

import com.hibernate.ferreteria.DTOs.CambiarRolDTO;
import com.hibernate.ferreteria.DTOs.UsuarioDTO;
import com.hibernate.ferreteria.DTOs.UsuarioRequestDTO;
import com.hibernate.ferreteria.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/usuarios")
    public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @GetMapping
        public List<UsuarioDTO> listar() {
            return usuarioService.listarUsuarios();
        }

        @PostMapping
        public ResponseEntity<?> crear(@RequestBody UsuarioRequestDTO request) {
            try {
                return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(request));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @PutMapping("/{id}/rol")
        public ResponseEntity<?> cambiarRol(@PathVariable Long id, @RequestBody CambiarRolDTO request) {
            try {
                return ResponseEntity.ok(usuarioService.cambiarRol(id, request.getRol()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @DeleteMapping("/{id}")
        public String eliminar(@PathVariable Long id) {
            return usuarioService.desactivarUsuario(id);
        }
    }

