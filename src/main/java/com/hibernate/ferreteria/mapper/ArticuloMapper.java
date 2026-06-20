package com.hibernate.ferreteria.mapper;

import com.hibernate.ferreteria.DTOs.ArticulosDTO;
import com.hibernate.ferreteria.entity.Articulo;

public class ArticuloMapper {

    public static ArticulosDTO toDTO(Articulo articulo) {
        return new ArticulosDTO(
                articulo.getId(),
                articulo.getNombre_articulo(),
                articulo.getPrecio(),
                articulo.getStock()
        );
    }

    public static Articulo toEntity(ArticulosDTO dto) {
        Articulo articulo = new Articulo();
        articulo.setId((int) dto.getId());
        articulo.setNombre_articulo(dto.getNombre_articulo());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());

        return articulo;
    }
}
