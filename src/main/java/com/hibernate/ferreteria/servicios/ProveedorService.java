package com.hibernate.ferreteria.servicios;

import com.hibernate.ferreteria.DTOs.AsociarArticulosDTO;
import com.hibernate.ferreteria.DTOs.ProveedorDTO;
import com.hibernate.ferreteria.DTOs.ProveedorRequestDTO;
import com.hibernate.ferreteria.entity.Articulo;
import com.hibernate.ferreteria.entity.Proveedor;
import com.hibernate.ferreteria.repositorio.Repo_articulos;
import com.hibernate.ferreteria.repositorio.Repo_proveedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    @Autowired
    private Repo_proveedores repoProveedores;

    @Autowired
    private Repo_articulos repoArticulos;

    public ProveedorDTO crear(ProveedorRequestDTO request) {
        Proveedor proveedor = new Proveedor();
        aplicarDatos(proveedor, request);
        return aDto(repoProveedores.save(proveedor));
    }

    public List<ProveedorDTO> listarTodos() {
        return repoProveedores.findAll().stream()
                .map(this::aDto)
                .collect(Collectors.toList());
    }

    public ProveedorDTO buscarPorId(Long id) {
        Proveedor proveedor = repoProveedores.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado: " + id));
        return aDto(proveedor);
    }

    public ProveedorDTO actualizar(Long id, ProveedorRequestDTO request) {
        Proveedor proveedor = repoProveedores.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado: " + id));
        aplicarDatos(proveedor, request);
        return aDto(repoProveedores.save(proveedor));
    }

    public void eliminar(Long id) {
        if (!repoProveedores.existsById(id)) {
            throw new IllegalArgumentException("Proveedor no encontrado: " + id);
        }
        repoProveedores.deleteById(id);
    }

    /**
     * Asocia una lista de articulos a un proveedor (relacion muchos a muchos).
     * Reemplaza el conjunto anterior de articulos asociados por el nuevo.
     */
    public ProveedorDTO asociarArticulos(Long proveedorId, AsociarArticulosDTO request) {
        Proveedor proveedor = repoProveedores.findById(proveedorId)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado: " + proveedorId));

        Set<Articulo> articulos = request.getArticuloIds().stream()
                .map(articuloId -> repoArticulos.findById(articuloId)
                        .orElseThrow(() -> new IllegalArgumentException("Articulo no encontrado: " + articuloId)))
                .collect(Collectors.toSet());

        proveedor.setArticulos(articulos);
        return aDto(repoProveedores.save(proveedor));
    }

    private void aplicarDatos(Proveedor proveedor, ProveedorRequestDTO request) {
        proveedor.setNombre(request.getNombre());
        proveedor.setCuit(request.getCuit());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setEmail(request.getEmail());
    }

    private ProveedorDTO aDto(Proveedor proveedor) {
        List<String> nombresArticulos = proveedor.getArticulos().stream()
                .map(Articulo::getNombre_articulo)
                .collect(Collectors.toList());

        return new ProveedorDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getCuit(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                nombresArticulos
        );
    }

}
