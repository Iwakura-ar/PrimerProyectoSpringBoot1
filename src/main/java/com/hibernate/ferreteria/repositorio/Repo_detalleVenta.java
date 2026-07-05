package com.hibernate.ferreteria.repositorio;

import com.hibernate.ferreteria.DTOs.ArticuloMasVendidoDTO;
import com.hibernate.ferreteria.entity.DetalleVenta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repo_detalleVenta extends JpaRepository<DetalleVenta, Long> {

    @Query("SELECT new com.hibernate.ferreteria.DTOs.ArticuloMasVendidoDTO(" +
            "vd.articulo.id, vd.articulo.nombre_articulo, SUM(vd.cantidad)) " +
            "FROM DetalleVenta vd " +
            "GROUP BY vd.articulo.id, vd.articulo.nombre_articulo " +
            "ORDER BY SUM(vd.cantidad) DESC")
    List<ArticuloMasVendidoDTO> findArticulosMasVendidos(Pageable pageable);
}