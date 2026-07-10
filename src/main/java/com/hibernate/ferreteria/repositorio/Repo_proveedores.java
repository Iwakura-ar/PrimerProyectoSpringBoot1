package com.hibernate.ferreteria.repositorio;

import com.hibernate.ferreteria.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Repo_proveedores extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByCuit(String cuit);
}
