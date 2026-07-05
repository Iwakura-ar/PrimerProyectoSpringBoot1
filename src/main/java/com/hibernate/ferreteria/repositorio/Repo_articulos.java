package com.hibernate.ferreteria.repositorio;

import com.hibernate.ferreteria.entity.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//JPARepository, para acceso a datos. Objeto Articulo, tipo a usar Long
public interface Repo_articulos extends JpaRepository<Articulo, Integer> {

    @Query("SELECT SUM(a.precio * a.stock) FROM Articulo a")
    Double calcularValorInventario();
}
