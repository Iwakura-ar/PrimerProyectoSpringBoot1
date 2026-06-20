package com.hibernate.ferreteria.servicios;

import com.hibernate.ferreteria.DTOs.ArticulosDTO;
import com.hibernate.ferreteria.entity.Articulo;
import com.hibernate.ferreteria.mapper.ArticuloMapper;
import com.hibernate.ferreteria.repositorio.Repo_articulos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ArticuloService {

    @Autowired
    private Repo_articulos repo;

    public List<ArticulosDTO> serv_consulta() {
        return repo.findAll().stream().map(ArticuloMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ArticulosDTO serv_buscaID(long id) {
        Articulo artPorId = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado: " + id));
        return ArticuloMapper.toDTO(artPorId);
    }

    public ArticulosDTO serv_insertarArticulo(ArticulosDTO dto) {
        Articulo articulo = ArticuloMapper.toEntity(dto);
        Articulo insertado = repo.save(articulo);
        return ArticuloMapper.toDTO(insertado);
    }

    public ArticulosDTO serv_actualizar(long id, ArticulosDTO dto) {
        Optional<Articulo> existe = repo.findById(id);

        if(existe.isPresent()) {
            Articulo articulo = existe.get();

            articulo.setNombre_articulo(dto.getNombre_articulo());
            articulo.setPrecio(dto.getPrecio());
            articulo.setStock(dto.getStock());

            Articulo actualizado = repo.save(articulo);

            return ArticuloMapper.toDTO(actualizado);
        }
        else {
            throw new RuntimeException("Articulo no encontrado con id: " + id);
        }
    }

    public String serv_eliminarArticulo(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Articulo eliminado correctamente";
        }
        else {
            return "No se encontró el artículo: " + id;
        }
    }

}
