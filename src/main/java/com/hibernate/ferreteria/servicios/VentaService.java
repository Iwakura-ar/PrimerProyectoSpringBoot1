package com.hibernate.ferreteria.servicios;

import com.hibernate.ferreteria.DTOs.DetalleRequestDTO;
import com.hibernate.ferreteria.DTOs.VentaRequestDTO;
import com.hibernate.ferreteria.entity.Articulo;
import com.hibernate.ferreteria.entity.DetalleVenta;
import com.hibernate.ferreteria.entity.Usuario;
import com.hibernate.ferreteria.entity.Venta;
import com.hibernate.ferreteria.repositorio.Repo_articulos;
import com.hibernate.ferreteria.repositorio.Repo_usuarios;
import com.hibernate.ferreteria.repositorio.Repo_ventas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private Repo_ventas repoVentas;

    @Autowired
    private Repo_articulos repoArticulos;

    @Autowired
    private Repo_usuarios repoUsuarios;

    /**
     * Registra una venta completa (cabecera + detalles) y descuenta stock.
     * Todo corre dentro de una unica transaccion: si algun producto no tiene
     * stock suficiente, se lanza una excepcion y Spring hace rollback de
     * TODOS los descuentos de stock ya aplicados en este metodo, no solo
     * del item que fallo.
     */
    @Transactional
    public Venta registrarVenta(VentaRequestDTO request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("ERROR: La venta debe tener al menos un producto");
        }

        Usuario usuario = repoUsuarios.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "ERROR: Usuario no encontrado: " + request.getUsuarioId()));

        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setUsuario(usuario);

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleRequestDTO item : request.getItems()) {

            Articulo articulo = repoArticulos.findById(item.getArticuloId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "ERROR: Articulo no encontrado: " + item.getArticuloId()));

            if (articulo.getStock() < item.getCantidad()) {
                // @Transactional, revierte toda la venta si el stock es insuficiente.
                throw new IllegalStateException(
                        "ERROR: Stock insuficiente para " + articulo.getNombre_articulo()
                                + " (disponible: " + articulo.getStock()
                                + ", solicitado: " + item.getCantidad() + ")");
            }

            articulo.setStock(articulo.getStock() - item.getCantidad());
            repoArticulos.save(articulo);

            BigDecimal precioUnitario = BigDecimal.valueOf(articulo.getPrecio());
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setArticulo(articulo);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
            total = total.add(subtotal);
        }

        venta.setDetalles(detalles);
        venta.setTotal(total);

        return repoVentas.save(venta);
    }

    public List<Venta> listarTodas() {
        return repoVentas.findAll();
    }

    public Optional<Venta> buscarPorId(Long id) {
        return repoVentas.findById(id);
    }
}