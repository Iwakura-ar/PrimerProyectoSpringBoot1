package com.hibernate.ferreteria.controladores;

import com.hibernate.ferreteria.DTOs.ArticulosDTO;
import com.hibernate.ferreteria.servicios.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
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
        public ArticulosDTO buscaID(@PathVariable Long id) {
                return servicio.serv_buscaID(id);
        }

        @PostMapping
        public ArticulosDTO insertarArticulo(@RequestBody ArticulosDTO dto) {
                return servicio.serv_insertarArticulo(dto);
        }

        @PutMapping("/{id}")
        public ArticulosDTO actualizar(@PathVariable long id,
                                       @RequestBody ArticulosDTO dto) {
                return servicio.serv_actualizar(id, dto);
        }

        @DeleteMapping("/{id}")
        public String eliminar(@PathVariable long id) {
                return servicio.serv_eliminarArticulo(id);
        }
}
