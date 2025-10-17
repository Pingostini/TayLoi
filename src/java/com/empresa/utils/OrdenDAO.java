package com.empresa.dao;

import com.empresa.model.OrdenModel;
import com.empresa.utils.ConexionDB;

import java.sql.*;
import java.util.*;

public class OrdenDAO {

    // Insertar una orden
    public void insertarOrden(OrdenModel orden) {
        String sql = "INSERT INTO orden (proveedor, fecha_orden, estado) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, orden.getProveedor());
            ps.setTimestamp(2, new java.sql.Timestamp(orden.getFechaOrden().getTime()));
            ps.setString(3, orden.getEstado());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar todas las órdenes
    public List<OrdenModel> listarOrdenes() {
        List<OrdenModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden ORDER BY fecha_orden DESC";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrdenModel o = new OrdenModel();
                o.setIdOrden(rs.getInt("id_orden"));
                o.setProveedor(rs.getString("proveedor"));
                o.setFechaOrden(rs.getTimestamp("fecha_orden"));
                o.setEstado(rs.getString("estado"));
                lista.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
