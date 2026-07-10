package com.hibernate.ferreteria.DTOs;

import java.util.List;

public class AsociarArticulosDTO {

    private List<Integer> articuloIds;

    public AsociarArticulosDTO() {
    }

    public List<Integer> getArticuloIds() {
        return articuloIds;
    }
    public void setArticuloIds(List<Integer> articuloIds) {
        this.articuloIds = articuloIds;
    }

}
