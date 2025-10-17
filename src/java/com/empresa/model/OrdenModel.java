package com.empresa.model;

import java.util.Date;

public class OrdenModel {
    private int idOrden;
    private String proveedor;
    private Date fechaOrden;
    private String estado;

    // Getters y Setters
    public int getIdOrden() {
        return idOrden;
    }
    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getProveedor() {
        return proveedor;
    }
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }
    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
