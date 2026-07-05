package com.hibernate.ferreteria.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;

    public class VentaPorFechaDTO {

        private LocalDate fecha;
        private Long cantidadVentas;
        private BigDecimal totalFacturado;

        public VentaPorFechaDTO(LocalDate fecha, Long cantidadVentas, BigDecimal totalFacturado) {
            this.fecha = fecha;
            this.cantidadVentas = cantidadVentas;
            this.totalFacturado = totalFacturado;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public Long getCantidadVentas() {
            return cantidadVentas;
        }

        public BigDecimal getTotalFacturado() {
            return totalFacturado;
        }
    }

