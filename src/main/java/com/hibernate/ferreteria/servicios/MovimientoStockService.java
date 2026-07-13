package com.hibernate.ferreteria.servicios;

import com.hibernate.ferreteria.DTOs.ArticuloStockBajoDTO;
import com.hibernate.ferreteria.DTOs.MovimientoStockDTO;
import com.hibernate.ferreteria.DTOs.MovimientoStockRequestDTO;
import com.hibernate.ferreteria.entity.Articulo;
import com.hibernate.ferreteria.entity.MovimientoStock;
import com.hibernate.ferreteria.entity.Usuario;
import com.hibernate.ferreteria.entity.TipoMovimiento;
import com.hibernate.ferreteria.repositorio.Repo_articulos;
import com.hibernate.ferreteria.repositorio.Repo_movimientoStock;
import com.hibernate.ferreteria.repositorio.Repo_usuarios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoStockService {

    @Autowired
    Repo_movimientoStock repoMovimientoStock;

    @Autowired
    Repo_articulos repoArticulos;

    @Autowired
    Repo_usuarios repoUsuarios;

    /**
     * Registra una entrada de mercaderia: suma stock al articulo y deja
     * constancia en el historial. Ej: llego un pedido de un proveedor.
     */
    @Transactional
    public MovimientoStockDTO registrarEntrada(MovimientoStockRequestDTO request) {
        Articulo articulo = repoArticulos.findById(request.getArticuloId()).
                orElseThrow(() -> new IllegalArgumentException("Articulo no encontrado: " +
                        request.getArticuloId()));

        articulo.setStock(articulo.getStock() + request.getCantidad());
        repoArticulos.save(articulo);

        MovimientoStock movimiento = crearMovimiento(articulo, TipoMovimiento.ENTRADA, request);
        repoMovimientoStock.save(movimiento);

        return aDto(movimiento, articulo.getStock());

    }

    /**
     * Registra una salida manual (rotura, ajuste de inventario, etc.).
     * Las salidas por venta ya las maneja VentaService directamente sobre
     * el stock del articulo; este metodo es para salidas que NO vienen de
     * una venta.
     */
    @Transactional
    public MovimientoStockDTO registrarSalida(MovimientoStockRequestDTO request) {
        Articulo articulo = repoArticulos.findById(request.getArticuloId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Articulo no encontrado: " + request.getArticuloId()));

        if (articulo.getStock() < request.getCantidad()) {
            throw new IllegalStateException(
                    "Stock insuficiente para " + articulo.getNombre_articulo()
                            + " (disponible: " + articulo.getStock()
                            + ", solicitado: " + request.getCantidad() + ")");
        }

        articulo.setStock(articulo.getStock() - request.getCantidad());
        repoArticulos.save(articulo);

        MovimientoStock movimiento = crearMovimiento(articulo, TipoMovimiento.SALIDA, request);
        repoMovimientoStock.save(movimiento);

        return aDto(movimiento, articulo.getStock());
    }

    public List<MovimientoStockDTO> historialPorArticulo(Integer articuloId) {
        return repoMovimientoStock.findByArticuloIdOrderByFechaDesc(articuloId).stream()
                .map(m -> aDto(m, null))
                .collect(Collectors.toList());
    }

    public List<ArticuloStockBajoDTO> listarStockBajo(int umbral) {
        return repoArticulos.findByStockLessThan(umbral).stream()
                .map(a -> new ArticuloStockBajoDTO(a.getId(), a.getNombre_articulo(),
                        a.getStock()))
                .collect(Collectors.toList());
    }

    private MovimientoStock crearMovimiento(Articulo articulo, TipoMovimiento tipo,
                                            MovimientoStockRequestDTO request) {
        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setArticulo(articulo);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo());
        movimiento.setFecha(LocalDateTime.now());

        if (request.getUsuarioId() != null) {
            Usuario usuario = repoUsuarios.findById(request.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Usuario no encontrado: " + request.getUsuarioId()));
            movimiento.setUsuario(usuario);
        }

        return movimiento;
    }

        private MovimientoStockDTO aDto(MovimientoStock m, Integer stockResultante) {
        return new MovimientoStockDTO(
                m.getId(),
                m.getArticulo().getId(),
                m.getArticulo().getNombre_articulo(),
                m.getTipo(),
                m.getCantidad(),
                m.getMotivo(),
                m.getFecha(),
                stockResultante
        );
    }
}
