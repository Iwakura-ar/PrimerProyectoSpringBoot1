package com.hibernate.ferreteria.repositorio;

import com.hibernate.ferreteria.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface Repo_ventas extends JpaRepository<Venta, Long> {

    // Devuelve filas crudas (fecha, cantidad de ventas, total facturado) agrupadas por dia.
    @Query(value =
            "SELECT DATE(v.fecha) as fecha, " +
                    "       COUNT(*) as cantidad_ventas, " +
                    "       SUM(v.total) as total_facturado " +
                    "FROM venta v " +
                    "WHERE v.fecha BETWEEN :desde AND :hasta " +
                    "GROUP BY DATE(v.fecha) " +
                    "ORDER BY DATE(v.fecha)",
            nativeQuery = true)
    List<Object[]> ventasPorPeriodoNative(@Param("desde") LocalDateTime desde,
                                          @Param("hasta") LocalDateTime hasta);
}

