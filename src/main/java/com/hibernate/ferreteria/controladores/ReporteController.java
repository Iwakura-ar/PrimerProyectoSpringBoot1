package com.hibernate.ferreteria.controladores;

import com.hibernate.ferreteria.DTOs.ArticuloMasVendidoDTO;
import com.hibernate.ferreteria.DTOs.VentaPorFechaDTO;
import com.hibernate.ferreteria.DTOs.ValorInventarioDTO;
import com.hibernate.ferreteria.servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

    @RestController
    @RequestMapping("/api/reportes")
    public class ReporteController {

        @Autowired
        private ReporteService reporteService;

        @GetMapping("/articulos-mas-vendidos")
        public List<ArticuloMasVendidoDTO> articulosMasVendidos(
                @RequestParam(defaultValue = "10") int limit) {
            return reporteService.obtenerArticulosMasVendidos(limit);
        }

        @GetMapping("/ventas-por-periodo")
        public List<VentaPorFechaDTO> ventasPorPeriodo(
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
            return reporteService.obtenerVentasPorPeriodo(desde, hasta);
        }

        @GetMapping("/valor-inventario")
        public ValorInventarioDTO valorInventario() {
            return reporteService.obtenerValorInventario();
        }
    }
