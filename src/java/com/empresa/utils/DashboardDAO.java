package com.empresa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    // Total de proveedores activos
    public static int getProveedoresActivos(Connection conn) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM proveedores WHERE activo=1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Total de recepciones
    public static int getTotalRecepciones(Connection conn) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM recepciones";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Recepciones pendientes
    public static int getRecepcionesPendientes(Connection conn) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM recepciones WHERE estado='pendiente'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Incidencias abiertas
    public static int getIncidenciasAbiertas(Connection conn) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM incidencias WHERE estado='abierto'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Eficiencia promedio = validaciones / total
    public static double getEficienciaPromedio(Connection conn) {
        double eficiencia = 0;
        try {
            String sqlTotal = "SELECT COUNT(*) FROM recepciones";
            String sqlValidadas = "SELECT COUNT(*) FROM recepciones WHERE estado='validada'";

            PreparedStatement psTotal = conn.prepareStatement(sqlTotal);
            ResultSet rsTotal = psTotal.executeQuery();
            int total = 0;
            if (rsTotal.next()) total = rsTotal.getInt(1);

            PreparedStatement psValidadas = conn.prepareStatement(sqlValidadas);
            ResultSet rsValidadas = psValidadas.executeQuery();
            int validadas = 0;
            if (rsValidadas.next()) validadas = rsValidadas.getInt(1);

            if (total > 0) {
                eficiencia = (validadas * 100.0) / total;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eficiencia;
    }

    // Tiempo promedio en minutos (si tienes fecha_inicio y fecha_fin)
    public static double getTiempoPromedio(Connection conn) {
        double tiempo = 0;
        try {
            String sql = "SELECT AVG(TIMESTAMPDIFF(MINUTE, fecha_inicio, fecha_fin)) " +
                         "FROM recepciones WHERE fecha_fin IS NOT NULL";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) tiempo = rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiempo;
    }
}
