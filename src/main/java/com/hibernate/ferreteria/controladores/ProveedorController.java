package com.hibernate.ferreteria.controladores;

import com.hibernate.ferreteria.DTOs.AsociarArticulosDTO;
import com.hibernate.ferreteria.DTOs.ProveedorDTO;
import com.hibernate.ferreteria.DTOs.ProveedorRequestDTO;
import com.hibernate.ferreteria.servicios.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProveedorRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.crear(request));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<ProveedorDTO> listar() {
        return proveedorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(proveedorService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProveedorRequestDTO request) {
        try {
            return ResponseEntity.ok(proveedorService.actualizar(id, request));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            proveedorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Asocia (reemplaza) los articulos que provee este proveedor
    @PutMapping("/{id}/articulos")
    public ResponseEntity<?> asociarArticulos(@PathVariable Long id, @RequestBody AsociarArticulosDTO request) {
        try {
            return ResponseEntity.ok(proveedorService.asociarArticulos(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/articulos/{articuloId}")
    public ResponseEntity<?> desasociarArticulo(@PathVariable Long id, @PathVariable Integer articuloId) {
        try {
            return ResponseEntity.ok(proveedorService.desasociarArticulo(id, articuloId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
