package com.hibernate.ferreteria.controladores;

import com.hibernate.ferreteria.DTOs.ArticulosDTO;
import com.hibernate.ferreteria.servicios.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/articulos")
@RestController
public class ArticuloController {

        @Autowired
        private ArticuloService servicio;

        @GetMapping
        public List<ArticulosDTO> listar() {
                return servicio.serv_consulta();
        }

        @GetMapping("/{id}")
        public ArticulosDTO buscaID(@PathVariable Integer id) {
                return servicio.serv_buscaID(id);
        }

        @PostMapping
        public ResponseEntity<?> insertarArticulo(@RequestBody ArticulosDTO dto) {
                try {
                        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.serv_insertarArticulo(dto));
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                }
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                       @RequestBody ArticulosDTO dto) {
                try {
                        return ResponseEntity.ok(servicio.serv_actualizar(id, dto));
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (RuntimeException e) {
                        return ResponseEntity.notFound().build();
                }
        }

        @DeleteMapping("/{id}")
        public String eliminar(@PathVariable Integer id) {
                return servicio.serv_eliminarArticulo(id);
        }

        @PutMapping("/{id}/activar")
        public String reactivar(@PathVariable Integer id) {
                return servicio.serv_reactivarArticulo(id);
        }

        @GetMapping("/inactivos")
        public List<ArticulosDTO> listarInactivos() {
                return servicio.serv_consultaInactivos();
        }
}
