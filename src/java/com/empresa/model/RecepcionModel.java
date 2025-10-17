package com.empresa.model;

import java.util.Date;
import java.util.List;

public class RecepcionModel {
    private int idRecepcion;
    private Integer idOrden; // puede ser null
    private String proveedor;
    private Date fechaRecepcion;
    private String estado;
    private String recibidoPor;
    private List<DetalleRecepcionModel> detalles;

    public int getIdRecepcion() { return idRecepcion; }
    public void setIdRecepcion(int idRecepcion) { this.idRecepcion = idRecepcion; }

    public Integer getIdOrden() { return idOrden; }
    public void setIdOrden(Integer idOrden) { this.idOrden = idOrden; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public Date getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(Date fechaRecepcion) { this.fechaRecepcion = fechaRecepcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getRecibidoPor() { return recibidoPor; }
    public void setRecibidoPor(String recibidoPor) { this.recibidoPor = recibidoPor; }

    public List<DetalleRecepcionModel> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRecepcionModel> detalles) { this.detalles = detalles; }
}
