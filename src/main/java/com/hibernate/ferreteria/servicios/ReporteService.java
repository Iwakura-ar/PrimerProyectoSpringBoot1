package com.hibernate.ferreteria.servicios;

import com.hibernate.ferreteria.DTOs.ArticuloMasVendidoDTO;
import com.hibernate.ferreteria.DTOs.VentaPorFechaDTO;
import com.hibernate.ferreteria.DTOs.ValorInventarioDTO;
import com.hibernate.ferreteria.repositorio.Repo_articulos;
import com.hibernate.ferreteria.repositorio.Repo_detalleVenta;
import com.hibernate.ferreteria.repositorio.Repo_ventas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private Repo_detalleVenta RepoDetalleVenta;

    @Autowired
    private Repo_ventas repoVentas;

    @Autowired
    private Repo_articulos repoArticulos;

    public List<ArticuloMasVendidoDTO> obtenerArticulosMasVendidos(int limit) {
        return RepoDetalleVenta.findArticulosMasVendidos(PageRequest.of(0, limit));
    }

    public List<VentaPorFechaDTO> obtenerVentasPorPeriodo(LocalDate desde, LocalDate hasta) {
        LocalDateTime desdeDt = desde.atStartOfDay();
        LocalDateTime hastaDt = hasta.atTime(23, 59, 59);

        List<Object[]> filas = repoVentas.ventasPorPeriodoNative(desdeDt, hastaDt);

        List<VentaPorFechaDTO> resultado = new ArrayList<>();

        for (Object[] fila : filas) {
            LocalDate fecha = ((java.sql.Date) fila[0]).toLocalDate();
            Long cantidadVentas = ((Number) fila[1]).longValue();
            BigDecimal totalFacturado = fila[2] != null
                    ? new BigDecimal(fila[2].toString())
                    : BigDecimal.ZERO;

            resultado.add(new VentaPorFechaDTO(fecha, cantidadVentas, totalFacturado));
        }
        return resultado;
    }

    public ValorInventarioDTO obtenerValorInventario() {
        Double valor = repoArticulos.calcularValorInventario();
        return new ValorInventarioDTO(valor != null ? valor : 0.0);
    }
}