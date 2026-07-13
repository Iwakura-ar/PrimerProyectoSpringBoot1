package com.hibernate.ferreteria.DTOs;

    public class ArticuloStockBajoDTO {

        private Integer articuloId;
        private String nombreArticulo;
        private Integer stock;

        public ArticuloStockBajoDTO(Integer articuloId, String nombreArticulo, Integer stock) {
            this.articuloId = articuloId;
            this.nombreArticulo = nombreArticulo;
            this.stock = stock;
        }

        public Integer getArticuloId() {
            return articuloId;
        }

        public void setArticuloId(Integer articuloId) {
            this.articuloId = articuloId;
        }

        public String getNombreArticulo() {
            return nombreArticulo;
        }

        public void setNombreArticulo(String nombreArticulo) {
            this.nombreArticulo = nombreArticulo;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }
