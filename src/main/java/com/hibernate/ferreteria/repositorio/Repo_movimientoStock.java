package com.hibernate.ferreteria.repositorio;

import com.hibernate.ferreteria.entity.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Repo_movimientoStock extends JpaRepository<MovimientoStock, Long> {
    List<MovimientoStock> findByArticuloIdOrderByFechaDesc(Integer articuloId);
}
