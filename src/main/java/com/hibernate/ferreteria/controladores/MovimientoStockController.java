package com.hibernate.ferreteria.controladores;

import com.hibernate.ferreteria.DTOs.ArticuloStockBajoDTO;
import com.hibernate.ferreteria.DTOs.MovimientoStockDTO;
import com.hibernate.ferreteria.DTOs.MovimientoStockRequestDTO;
import com.hibernate.ferreteria.servicios.MovimientoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacen")
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService movimientoService;

    @PostMapping("/entradas")
    public ResponseEntity<?> registrarSalida(@RequestBody MovimientoStockRequestDTO request) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(movimientoService.registrarEntrada(request));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salidas")
    public ResponseEntity<?> registrarEntrada(@RequestBody MovimientoStockRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(movimientoService.registrarSalida(request));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/articulo/{articuloId}")
    public List<MovimientoStockDTO> historialPorArticulo(@PathVariable Integer articuloId) {
        return movimientoService.historialPorArticulo(articuloId);
    }

    @GetMapping("/stock-bajo")
    public List<ArticuloStockBajoDTO> stockBajo(@RequestParam(defaultValue = "10") int umbral) {
        return movimientoService.listarStockBajo(umbral);
    }
}
