package com.empresa.dao;

import com.empresa.model.RecepcionModel;
import com.empresa.model.DetalleRecepcionModel;
import com.empresa.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecepcionDAO {

    // Inserta cabecera y devuelve id_recepcion generado
    public int insertarRecepcion(RecepcionModel r) {
        int idGenerado = 0;
        String sql = "INSERT INTO recepcion (id_orden, proveedor, fecha_recepcion, estado, recibido_por) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (r.getIdOrden() != null) ps.setInt(1, r.getIdOrden());
            else ps.setNull(1, java.sql.Types.INTEGER);

            ps.setString(2, r.getProveedor());
            ps.setTimestamp(3, new java.sql.Timestamp(r.getFechaRecepcion().getTime()));
            ps.setString(4, r.getEstado());
            ps.setString(5, r.getRecibidoPor());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) idGenerado = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    // Inserta un detalle (ya con id_recepcion)
    public void insertarDetalle(DetalleRecepcionModel d) {
        String sql = "INSERT INTO detalle_recepcion (id_recepcion, producto, cantidad) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getIdRecepcion());
            ps.setString(2, d.getProducto());
            ps.setInt(3, d.getCantidad());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar recepciones con detalles
    public List<RecepcionModel> listarRecepciones() {
        List<RecepcionModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM recepcion ORDER BY fecha_recepcion DESC";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RecepcionModel r = new RecepcionModel();
                r.setIdRecepcion(rs.getInt("id_recepcion"));
                int idOrden = rs.getInt("id_orden");
                if (rs.wasNull()) r.setIdOrden(null); else r.setIdOrden(idOrden);
                r.setProveedor(rs.getString("proveedor"));
                r.setFechaRecepcion(rs.getTimestamp("fecha_recepcion"));
                r.setEstado(rs.getString("estado"));
                r.setRecibidoPor(rs.getString("recibido_por"));
                r.setDetalles(obtenerDetalles(r.getIdRecepcion()));
                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<DetalleRecepcionModel> obtenerDetalles(int idRecepcion) {
        List<DetalleRecepcionModel> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_recepcion WHERE id_recepcion = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRecepcion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleRecepcionModel d = new DetalleRecepcionModel();
                    d.setIdDetalle(rs.getInt("id_detalle"));
                    d.setIdRecepcion(rs.getInt("id_recepcion"));
                    d.setProducto(rs.getString("producto"));
                    d.setCantidad(rs.getInt("cantidad"));
                    detalles.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalles;
    }

    // Actualizar estado (por ejemplo a 'completada')
    public void actualizarEstado(int idRecepcion, String nuevoEstado) {
        String sql = "UPDATE recepcion SET estado = ? WHERE id_recepcion = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idRecepcion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
