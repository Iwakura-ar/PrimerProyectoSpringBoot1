package com.hibernate.ferreteria.repositorio;

import com.hibernate.ferreteria.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo_ventas extends JpaRepository<Venta, Long> {
}
